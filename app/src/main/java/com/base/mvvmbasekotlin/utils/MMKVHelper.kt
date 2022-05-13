package com.base.mvvmbasekotlin.utils

import com.tencent.mmkv.MMKV

object MMKVHelper {
    private val kv : MMKV by lazy {
       MMKV.defaultMMKV()
    }

    fun getInstance() :MMKV{
        return kv;
    }
}