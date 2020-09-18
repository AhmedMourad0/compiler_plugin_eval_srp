package dev.ahmedmourad.eval

import arrow.meta.CliPlugin
import arrow.meta.Meta
import arrow.meta.internal.Noop
import arrow.meta.invoke
import arrow.meta.phases.CompilerContext
import arrow.meta.phases.analysis.AnalysisHandler
import arrow.meta.quotes.Transform
import arrow.meta.quotes.namedFunction
import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.js.descriptorUtils.getJetTypeFqName
import org.jetbrains.kotlin.js.translate.callTranslator.getReturnType
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.BindingTrace
import org.jetbrains.kotlin.resolve.calls.callUtil.getResolvedCall
import javax.script.ScriptException

val Meta.evalExtension: CliPlugin
    get() = "Eval" {
        meta(impl())
    }

internal fun Meta.impl(): AnalysisHandler = analysis(
    doAnalysis = Noop.nullable7<AnalysisResult>(),
    analysisCompleted = { _, _, _, _ ->
        try {
            messageCollector?.report(
                CompilerMessageSeverity.ERROR,
                eval("\"X\"").toString(),
                null
            )
        } catch (e: ScriptException) {
            messageCollector?.report(
                CompilerMessageSeverity.ERROR,
                e.toString(),
                null
            )
        }
        null
    }
)
