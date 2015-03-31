The core of the simulator contains:
- Variable
- Value
- Environment
- Simulation

Variables are the abstraction for agents in multiagent systems. They have values that must be optimized.

Values are the abstraction for tasks, events, actions or any interaction that variables may have to modify the environment.

Environment is the "stage". The Environment is composed by set of agents and set of objects. The agents may perform actions (choose one or more values) to interact with objects in scenario.

For example:
One environment has fire fighter agents (variables) and buildings. Some random event are throwed such that one of the buildings starts to burn. Fire fighters must choose what actions to perform:

- extinguish fire from building
- send a message and call for help
- get inside the building to save people and so on

In some way, each action makes the fire fighter to interact with the building to save people, minimize damage or avoid the fire to propagate to other buildings.

The agents and objects are inside the environment. The interaction between them produces the environment behavior.

The simulation contains the rules that control the interaction of the agents and objects. How much each action of the agent modifies the objects, or how many objects can be modified by on agents.
At the same time, simulation rules control how much the objects react to the actions.

For example:
Fire fighter choose to extinguish fire from the building #1. One apartment of the building #1 is on fire.
Fire fighter throws water to the apartment.
At the next turn, the fire level reduces.

In general, the abstract simulator is based in some ideas of Gaia Methodology (see WOOLDRIDGE, Michael - Introduction to Multiagent systems).

Variables and values are abstract concepts to formalize the entire system. At the same time that agents and tasks are concrete concepts. The environment is the set of agents, objects, interactions and every kind of behavior that will emerge from the system.