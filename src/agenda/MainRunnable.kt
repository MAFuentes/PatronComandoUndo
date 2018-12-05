package agenda

data class Persona(val id: Int,val name: String, var telefono: String)
class Agenda(val actions: Actions, private val personas: MutableList<Persona> = arrayListOf(), private var count: Int = 0){
    class ActionAddPersona : Action(){
        var persona: Persona? = null
        var personas: MutableList<Persona>? = null
        fun hacer(personas: MutableList<Persona>, persona: Persona) {
            this.persona = persona
            this.personas = personas
            personas.add(persona)
            println("Add $persona")

        }
        override fun deshacer() {
            personas?.remove(persona)
            println("Undo added:$persona")
        }

    }
    class ActionDeletePersona : Action(){
        var persona: Persona? = null
        var index: Int = 0
        var personas: MutableList<Persona>? = null
        fun hacer(personas: MutableList<Persona>, persona: Persona) {
            this.persona = persona
            this.personas = personas
            index = personas.indexOf(persona)
            personas.removeAt(index)
            println("Delete $persona")
        }
        override fun deshacer() {
            personas!!.add(index, persona!!)
            println("Undo delete $persona")
        }

    }
    fun addPersona(nombre:String,telefono: String){
        val persona = Persona(count,nombre,telefono)
        val actionAddPersona = ActionAddPersona()
        actionAddPersona.hacer(personas,persona)
        actions.add(actionAddPersona)
        count++
    }
    fun deletePersona(nombre: String){
        val persona = personas.find { persona -> persona.name == nombre }
        if (persona != null) {
            val actionDeletePersona = ActionDeletePersona()
            actionDeletePersona.hacer(personas,persona)
            actions.add(actionDeletePersona)
        } else
            println("Cannot find and delete $nombre")
    }
    fun showPersona(){
        println(personas)
    }
}
abstract class Action{
    abstract fun deshacer()
}
class Actions (private val actions: MutableList<Action> = arrayListOf()){
    fun add(action: Action){
        actions.add(action)
    }
    fun undo(){
        actions[actions.size - 1].deshacer()
        actions.removeAt(actions.size - 1)
    }
}
fun main(args: Array<String>) {
    val actions = Actions()
    val agenda = Agenda(actions)
    agenda.addPersona("Perico","623 45 67 29")
    agenda.addPersona("Lora","610 45 67 89")
    agenda.addPersona("Gumercindo","606 45 67 11")
    agenda.deletePersona("Perico")
    for (i in 1..2){
        actions.undo()
    }
    agenda.showPersona()

}
