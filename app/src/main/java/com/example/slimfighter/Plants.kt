package com.example.slimfighter

import android.widget.ImageView
import java.util.*

enum class GrowCycle{
    NONE,PLANT,WATERED,GROWN,FULLYGROWN
}

enum class PlantName{
    DAISY,EMPTY,ROSE,SUNFLOWER,CENTAUREA,WATERGRASS,MUSHROOM,TULIPS
}

class Plants(val name: PlantName,var field: ImageView?) {
    var listPosition = -1
    var totalGrowTime: Long = 0
    var leftGrowTime: Long = 0
    var actualCycle: GrowCycle = GrowCycle.NONE
    var seed: Int = -1
    var watered: Int = -1
    var grown: Int = -1
    var wateredGrown: Int = -1
    private val zero: Long = 0
    var value: Int = -1

    init {
        if (PlantName.EMPTY != name){
            field?.setImageResource(R.drawable.emptyground)
            field?.setTag(-69,R.drawable.emptyground)
            when(name){
                PlantName.DAISY -> {
                    seed=R.drawable.seeds
                    watered = R.drawable.watteredseeds
                    grown = R.drawable.daisy
                    wateredGrown = R.drawable.wattereddaisy
                    totalGrowTime=5000
                    listPosition=0
                    value=5
                }
                PlantName.ROSE -> {
                    seed=R.drawable.roseseeds
                    watered = R.drawable.rosewatteredseeds
                    grown = R.drawable.rose
                    wateredGrown = R.drawable.watteredrose
                    totalGrowTime=10000
                    listPosition=1
                    value=10
                }
                PlantName.SUNFLOWER -> {
                    seed=R.drawable.sunflowerseeds
                    watered = R.drawable.watersunseeds
                    grown = R.drawable.sunflower
                    wateredGrown = R.drawable.watteredsunflower
                    totalGrowTime=1000
                    listPosition=2
                    value =2
                }
                PlantName.TULIPS -> {
                    seed=R.drawable.tulipseeds
                    watered = R.drawable.watteredtulipseeds
                    grown = R.drawable.tulips
                    wateredGrown = R.drawable.watteredtulips
                    totalGrowTime=25000
                    listPosition=3
                    value =25
                }
                PlantName.WATERGRASS -> {
                    seed=R.drawable.grassseeds
                    watered = R.drawable.wateredgrassseeds
                    grown = R.drawable.watergrass
                    wateredGrown = R.drawable.waterwatergrass
                    totalGrowTime=12000
                    listPosition=4
                    value =20
                }
                PlantName.MUSHROOM -> {
                    seed=R.drawable.mushromdseeds
                    watered = R.drawable.watteredmushromseeds
                    grown = R.drawable.mushrom
                    wateredGrown = R.drawable.watteredmushrom
                    totalGrowTime=7000
                    listPosition=5
                    value =15
                }
                PlantName.CENTAUREA -> {
                    seed=R.drawable.chabrseeds
                    watered = R.drawable.watteredchabrseeds
                    grown = R.drawable.chabr
                    wateredGrown = R.drawable.watteredchabr
                    totalGrowTime=20000
                    listPosition=6
                    value =40
                }
                else -> {}
            }
        }
    }

    fun plant(){
        if (actualCycle==GrowCycle.NONE){
            actualCycle = GrowCycle.PLANT
            if (MyVariables.isAutoWaterOn){
                field?.setImageResource(watered)
            } else {
                field?.setImageResource(seed)
            }

            if (MyVariables.reduceGrowTime>0){
                totalGrowTime = ((1-0.1*MyVariables.reduceGrowTime)*totalGrowTime).toLong()
            }

            val timer  = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    if (actualCycle == GrowCycle.PLANT) {
                        field?.setImageResource(grown)
                        actualCycle = GrowCycle.GROWN
                    } else if (actualCycle == GrowCycle.WATERED) {
                        field?.setImageResource(wateredGrown)
                        actualCycle = GrowCycle.FULLYGROWN
                    }
                    if (MyVariables.isAutoWaterOn){
                        field?.setImageResource(wateredGrown)
                        actualCycle = GrowCycle.FULLYGROWN
                    }
                    if (MyVariables.isAutoHarvestOn){
                        harvest()
                        MyVariables.flowersText[listPosition].text = "${MyVariables.flowersCount[listPosition]}"
                    }
                }
            },totalGrowTime)
        }
    }

    fun water(){
        if (actualCycle==GrowCycle.PLANT){
            actualCycle=GrowCycle.WATERED
            field?.setImageResource(watered)
        }
    }

    fun harvest(){
        if (actualCycle==GrowCycle.GROWN){
            MyVariables.flowersCount[listPosition]++
            field?.setImageResource(R.drawable.emptyground)
            actualCycle=GrowCycle.NONE
            MyVariables.flowersText[listPosition].text = "${MyVariables.flowersCount[listPosition]}"
        } else if (actualCycle==GrowCycle.FULLYGROWN){
            MyVariables.flowersCount[listPosition]++
            MyVariables.flowersCount[listPosition]++
            if (MyVariables.increaseGrowLevel>=1){
                for (i in 1..MyVariables.increaseGrowLevel){
                    MyVariables.flowersCount[listPosition]++
                }
            }
            field?.setImageResource(R.drawable.emptyground)
            actualCycle=GrowCycle.NONE
            MyVariables.flowersText[listPosition].text = "${MyVariables.flowersCount[listPosition]}"
        }
    }


}