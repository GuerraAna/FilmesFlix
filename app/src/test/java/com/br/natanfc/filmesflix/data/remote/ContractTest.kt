package com.br.natanfc.filmesflix.data.remote

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import org.junit.Assume.assumeTrue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.time.LocalDate

/**
 * Contract test: validates that the app DTOs serialize/deserialize JSON matching
 * the schemas declared in contracts/openapi.yaml (workspace root).
 *
 * Only runs inside the workspace (apps/filmes-flix/app -> ../../../contracts).
 * In a standalone clone of FilmesFlix the contract file is absent and the
 * tests are skipped via JUnit assumptions.
 */
class ContractTest {

    private val json = Json { encodeDefaults = true }
    private val contractFile = File("../../../contracts/openapi.yaml")

    @Suppress("UNCHECKED_CAST")
    private fun loadContract(): Map<String, Any> {
        assumeTrue(
            "contracts/openapi.yaml not found — contract tests only run inside the workspace",
            contractFile.exists()
        )
        return Yaml().load(contractFile.inputStream()) as Map<String, Any>
    }

    @Suppress("UNCHECKED_CAST")
    private fun schema(contract: Map<String, Any>, name: String): Map<String, Any> {
        val schemas = (contract["components"] as Map<String, Any>)["schemas"] as Map<String, Any>
        val raw = schemas[name] as? Map<String, Any> ?: error("Schema '$name' missing from openapi.yaml")

        // Resolve allOf: merge properties and required from every part
        val properties = mutableMapOf<String, Map<String, Any>>()
        val required = mutableListOf<String>()
        val parts = (raw["allOf"] as? List<Map<String, Any>>)?.map { part ->
            (part["\$ref"] as? String)?.removePrefix("#/components/schemas/")
                ?.let { schema(contract, it) } ?: part
        } ?: listOf(raw)
        for (part in parts) {
            properties.putAll(part["properties"] as? Map<String, Map<String, Any>> ?: emptyMap())
            required.addAll(part["required"] as? List<String> ?: emptyList())
        }
        return mapOf("properties" to properties, "required" to required)
    }

    @Suppress("UNCHECKED_CAST")
    private fun assertMatchesSchema(
        contract: Map<String, Any>,
        serialized: Map<String, Any?>,
        schemaName: String
    ) {
        val resolved = schema(contract, schemaName)
        val properties = resolved["properties"] as Map<String, Map<String, Any>>
        val required = resolved["required"] as List<String>

        for (field in required) {
            assertTrue("Required field '$field' missing in $schemaName payload", serialized.containsKey(field))
            assertNotNull("Required field '$field' is null in $schemaName payload", serialized[field])
        }
        for ((key, value) in serialized) {
            val spec = properties[key] ?: error("Field '$key' not declared in schema $schemaName")
            if (value == null) continue
            when (spec["type"]) {
                "integer" -> assertTrue("$schemaName.$key must be integer, got ${value::class}", value is Number)
                "string" -> assertTrue("$schemaName.$key must be string, got ${value::class}", value is String)
                "array" -> assertTrue("$schemaName.$key must be array", value is List<*>)
            }
            if (spec["format"] == "date") {
                LocalDate.parse(value as String) // throws on non-ISO date
            }
            if (spec["format"] == "uri") {
                assertTrue("$schemaName.$key must be an uri string", (value as String).contains("://"))
            }
        }
    }

    private fun JsonElement.toPlain(): Any? = when (this) {
        JsonNull -> null
        is JsonPrimitive -> if (isString) content
            else content.toIntOrNull() ?: content.toLongOrNull()
                ?: content.toDoubleOrNull() ?: content.toBooleanStrictOrNull() ?: content
        is JsonArray -> map { it.toPlain() }
        is JsonObject -> mapValues { (_, value) -> value.toPlain() }
    }

    private fun serialize(dto: MovieSummaryDto): Map<String, Any?> =
        json.encodeToJsonElement(dto).jsonObject.toPlain() as Map<String, Any?>

    private fun serialize(dto: MovieDetailDto): Map<String, Any?> =
        json.encodeToJsonElement(dto).jsonObject.toPlain() as Map<String, Any?>

    @Test
    fun `MovieSummaryDto matches MovieSummary schema`() {
        val contract = loadContract()
        val dto = MovieSummaryDto(id = 1, title = "Interstellar", posterUrl = "http://poster")
        assertMatchesSchema(contract, serialize(dto), "MovieSummary")
    }

    @Test
    fun `MovieDetailDto matches MovieDetail schema`() {
        val contract = loadContract()
        val dto = MovieDetailDto(
            id = 1, title = "Interstellar", posterUrl = "http://poster",
            overview = "overview", releaseDate = "2014-11-06"
        )
        assertMatchesSchema(contract, serialize(dto), "MovieDetail")
    }

    @Test
    fun `BFF payload deserializes into app DTOs`() {
        val contract = loadContract()
        // Simulates a wire payload as declared by the contract, then feeds it into the DTOs.
        val summaryJson = """{"id":7,"title":"Dune","posterUrl":"https://image/t/p.jpg"}"""
        val summary = json.decodeFromString<MovieSummaryDto>(summaryJson)
        assertMatchesSchema(contract, serialize(summary), "MovieSummary")
        assertEquals("Dune", summary.title)

        val detailJson = """{"id":7,"title":"Dune","posterUrl":"https://image/t/p.jpg","overview":"Epic","releaseDate":"2021-10-22"}"""
        val detail = json.decodeFromString<MovieDetailDto>(detailJson)
        assertMatchesSchema(contract, serialize(detail), "MovieDetail")
        assertEquals("2021-10-22", detail.releaseDate)
    }
}
