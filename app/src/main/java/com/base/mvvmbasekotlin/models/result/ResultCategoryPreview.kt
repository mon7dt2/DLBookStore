package com.base.mvvmbasekotlin.models.result

import com.base.mvvmbasekotlin.models.data.CategoryPreview

data class ResultCategoryPreview(val results: ArrayList<CategoryPreview>, val pageIndex: Int,
                    val pageSize: Int, val totalPage: Int, val totalItem: Int)