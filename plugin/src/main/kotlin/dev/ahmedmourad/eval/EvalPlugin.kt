package dev.ahmedmourad.eval

import arrow.meta.CliPlugin
import arrow.meta.Meta
import arrow.meta.phases.CompilerContext
import kotlin.contracts.ExperimentalContracts

class EvalPlugin : Meta {
    @ExperimentalContracts
    override fun intercept(ctx: CompilerContext): List<CliPlugin> = listOf(
        evalExtension
    )
}
