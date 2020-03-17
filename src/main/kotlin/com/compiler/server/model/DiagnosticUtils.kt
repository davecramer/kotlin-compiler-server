package com.compiler.server.model

val ErrorDescriptor.isError: Boolean
    get() = severity == ProjectSeveriry.ERROR

val ExecutionResult.hasErrors: Boolean
    get() = errors.hasErrors

val Map<String, List<ErrorDescriptor>>.hasErrors: Boolean
    get() = asSequence()
            .flatMap { it.value.asSequence() }
            .any { it.isError }


val ExecutionResult.filterOnlyErrors: List<ErrorDescriptor>
    get() = errors.filterOnlyErrors

val Map<String, List<ErrorDescriptor>>.filterOnlyErrors: List<ErrorDescriptor>
    get() = flatMap { it.value }.filter { it.isError }


fun renderErrorDescriptors(errors: List<ErrorDescriptor>): String {
    return buildString {
        errors.forEach { appendln(it.renderErrorDescriptor()) }
    }
}

fun ErrorDescriptor.renderErrorDescriptor(): String = "(${interval.start.line}, ${interval.end.line}): $message"
