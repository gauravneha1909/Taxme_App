package com.example.gplusin.demo

data class Data(val total: Int = 0,
                val data: List<DataItem>?,
                val limit: Int = 0,
                val page: Int = 0)