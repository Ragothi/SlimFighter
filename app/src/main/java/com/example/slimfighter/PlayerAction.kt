package com.example.slimfighter

enum class ActionType{
    ATTACK,HEAL,DEFEND,MAGIC
}

class PlayerAction(val actionType: ActionType,var value: Int,val icon: Int) {
}