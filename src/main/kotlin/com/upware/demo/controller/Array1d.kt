package com.upware.demo.controller

import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.Serializable

@RestController
@CrossOrigin
@RequestMapping("/1d")
class Array1d {
    val file = File("D:1dArray.txt")
    var array: Array<String?> = arrayOfNulls(5)
    var empty = 0

    @GetMapping("/getAll")
    fun getAll(): Array<String?> {
        getData()
        return array
    }

    @GetMapping("/{index}")
    fun getById(@PathVariable("index") index: Int): String {
        getData()
        return if (index <= 5) array[index - 1].toString() else "Value not exists"
    }

    @GetMapping("/search/{value}")
    fun search(@PathVariable("value") value: String): String {
        getData()
        return if (array.contains(value)) "${array.indexOf(value) + 1}: $value" else "Not exists"
    }

    @PostMapping("/{value}")
    fun insert(@PathVariable("value") value: String): Serializable {
        getData()
        try {
            array[array.indexOf(null)] = value
            file.appendText(value + "\n")
        } catch (e: Exception) {
            if (array.contains("null")) {
                update(array.indexOf("null") + 1, value)
                return getAll()
            }
            return "Array is full!"
        }

        return "$value is inserted"
    }

    @PutMapping("/{id}/{value}")
    fun update(@PathVariable("id") id: Int, @PathVariable("value") value: String): Array<String?> {
        getData()
        array[id - 1] = value
        file.bufferedWriter().flush()
        array.forEach {
            file.appendText(it + "\n")
        }
        return array
    }

    private fun getData() {
        var i: Int = 0
        file.forEachLine {
            if (i > 4) return@forEachLine
            array[i] = it
            i++
        }
    }
}