package com.desager.arch.tea.model

class MessageBuilder<State : Any, Action : Any, Command : Any>(
    initialState: State,
) {

    var state: State = initialState
        private set

    private var actions = listOf<Action>()

    private var commands = listOf<Command>()

    fun state(update: State.() -> State) {
        this.state = state.run(update)
    }

    fun actions(vararg actions: Action) {
        this.actions = listOf(*actions)
    }

    fun commands(vararg commands: Command) {
        this.commands = listOf(*commands)
    }

    internal fun build(): Message<State, Action, Command> = Message(state, actions, commands)
}