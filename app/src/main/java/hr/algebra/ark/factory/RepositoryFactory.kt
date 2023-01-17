package hr.algebra.ark.factory

import android.content.Context
import hr.algebra.ark.dao.ArkSqlHelper

fun getArkRepository(context: Context?) = ArkSqlHelper(context)