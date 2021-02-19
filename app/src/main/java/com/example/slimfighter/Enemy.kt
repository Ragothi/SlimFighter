package com.example.slimfighter

import android.widget.ImageView
import kotlin.random.Random.Default.nextInt

enum class Type{
    PHYSICAL,MAGICAL
}
enum class EnemyLevel{
    ONE,TWO,THREE,FOUR
}

class Enemy(val  enemyName: String, val type: Type, val maxHp: Int, var atk: Int, val enemyImage: Int, private val level: EnemyLevel) {
    var actualHp = maxHp
    var value = 0

    init {

    }

    fun reRollValue(){
        value = when(level){
            EnemyLevel.ONE -> nextInt(10,20)
            EnemyLevel.TWO -> nextInt(20,30)
            EnemyLevel.THREE -> nextInt(30,40)
            EnemyLevel.FOUR -> nextInt(500,1000)

        }
    }

    fun getLevel() : Int{
        return when (level){
            EnemyLevel.ONE -> 1
            EnemyLevel.TWO -> 2
            EnemyLevel.THREE -> 3
            EnemyLevel.FOUR -> 4
        }
    }

    fun receiveDamage(action: PlayerAction){
        var value: Int = 0
        if (action.actionType.equals(ActionType.ATTACK)){
            value = MyVariables.atkStatValue
        } else if (action.actionType.equals(ActionType.MAGIC)){
            value = MyVariables.magicStatValue
        }

        if (action.actionType.equals(ActionType.ATTACK) && type.equals(Type.PHYSICAL)){
            actualHp -= value/2
        } else if (action.actionType.equals(ActionType.ATTACK) && type.equals(Type.MAGICAL)){
            actualHp -= value
        } else if (action.actionType.equals(ActionType.MAGIC) && type.equals(Type.MAGICAL)){
            actualHp -= value/3
        } else if (action.actionType.equals(ActionType.MAGIC) && type.equals(Type.PHYSICAL)){
            actualHp -= value
        }
    }

}