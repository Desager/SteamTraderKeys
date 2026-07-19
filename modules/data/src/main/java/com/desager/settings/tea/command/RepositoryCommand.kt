package com.desager.settings.tea.command

import com.desager.settings.model.Arguments

internal sealed interface RepositoryCommand {

    class Invalidate(val args: Arguments) : RepositoryCommand
}