package io.github.lingomate

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringExtension

@Suppress("unused")
object ProjectConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(SpringExtension)
}