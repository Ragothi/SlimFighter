package com.example.slimfighter

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.slimfighter.MyVariables.actualFlowerType
import com.example.slimfighter.MyVariables.actualPlayerHealth
import com.example.slimfighter.MyVariables.atkPrice
import com.example.slimfighter.MyVariables.atkStatValue
import com.example.slimfighter.MyVariables.autoLevel
import com.example.slimfighter.MyVariables.canAlchemy
import com.example.slimfighter.MyVariables.canAutoPlant
import com.example.slimfighter.MyVariables.canPlant
import com.example.slimfighter.MyVariables.firstTimeOpenHarvest
import com.example.slimfighter.MyVariables.flowersCount
import com.example.slimfighter.MyVariables.flowersText
import com.example.slimfighter.MyVariables.gold
import com.example.slimfighter.MyVariables.healPrice
import com.example.slimfighter.MyVariables.healStatValue
import com.example.slimfighter.MyVariables.increaseGrowLevel
import com.example.slimfighter.MyVariables.isAutoHarvestOn
import com.example.slimfighter.MyVariables.isAutoPlantOn
import com.example.slimfighter.MyVariables.isAutoWaterOn
import com.example.slimfighter.MyVariables.isSkillBought
import com.example.slimfighter.MyVariables.level
import com.example.slimfighter.MyVariables.loadGame
import com.example.slimfighter.MyVariables.magicPrice
import com.example.slimfighter.MyVariables.magicStatValue
import com.example.slimfighter.MyVariables.maxPlayerHealth
import com.example.slimfighter.MyVariables.playerShieldValue
import com.example.slimfighter.MyVariables.randomLeft
import com.example.slimfighter.MyVariables.randomRight
import com.example.slimfighter.MyVariables.reduceGrowTime
import com.example.slimfighter.MyVariables.rollEnemy
import com.example.slimfighter.MyVariables.saveGame
import com.example.slimfighter.MyVariables.selectedFlowerSkillToBuy
import com.example.slimfighter.MyVariables.shieldPrice
import com.example.slimfighter.MyVariables.shieldStatValue
import com.example.slimfighter.MyVariables.sizeOfFlowersList
import com.example.slimfighter.MyVariables.toNextAttack
import kotlin.collections.ArrayList


object MyVariables {
    const val sizeOfFlowersList = 6
    const val bossLevel: Int = 50
    var flowersCount: ArrayList<Int> = ArrayList(sizeOfFlowersList)
    var flowersText: ArrayList<TextView> = ArrayList(sizeOfFlowersList)
    var level = 1
    var canPlant: ArrayList<Boolean> = ArrayList(sizeOfFlowersList)
    var isSkillBought: ArrayList<Boolean> = ArrayList(7)
    var gold: Int = 0
    var actualPlayerHealth: Int = 20
    var maxPlayerHealth: Int = 20
    var actualEnemy: Enemy = Enemy("Something went wrong", Type.PHYSICAL, -1, -1, 0, EnemyLevel.ONE)
    var rollEnemy: Boolean = true
    var toNextAttack: Int = 3
    var randomLeft: Int = 0
    var randomRight: Int = 0
    var playerShieldValue: Int = 0
    var actualFlowerType: Int = 0
    var atkPrice: Int = 40
    var healPrice: Int = 35
    var magicPrice: Int = 45
    var shieldPrice: Int = 60
    var atkStatValue: Int = 0
    var healStatValue: Int = 0
    var magicStatValue: Int = 0
    var shieldStatValue: Int = 0
    var firstTimeOpenHarvest: Boolean = true
    var selectedFlowerSkillToBuy: flowerSkillType = flowerSkillType.NONE
    var firstRunMain: Boolean = true
    var canAutoPlant: Boolean = false
    var isAutoPlantOn: Boolean = false
    var isAutoWaterOn: Boolean = false
    var isAutoHarvestOn: Boolean = false
    var increaseGrowLevel: Int = 0
    var reduceGrowTime: Int = 0
    var canAlchemy: Boolean = false
    var autoLevel: Int = 0
    var isHealStatMaxed: Boolean = false
    var isShieldStatMaxed: Boolean = false
    var playMusic: Boolean = true



    enum class flowerSkillType{
        NONE,DAISY,ROSE,SUNFLOWER,TULIP,WATERGRASS,MUSHROOM,CENTAUREA,AUTO_HARVEST,AUTO_WATER,AUTO_PLANT,INCREASE_GROW_AMOUNT,UNLOCK_ALCHEMY,REDUCE_GROW_TIME
    }

    fun saveGame(pref: SharedPreferences?){
        val editor = pref?.edit()
        for (i in 0..sizeOfFlowersList){
            editor?.putInt("flower$i", flowersCount[i])
            editor?.putBoolean("can$i", canPlant[i])
            editor?.putBoolean("isbought$i", isSkillBought[i])
        }

        editor?.putInt("level", level)
        editor?.putInt("gold", gold)
        editor?.putInt("actualPlayerHP", actualPlayerHealth)
        editor?.putInt("maxPlayerHP", maxPlayerHealth)
        editor?.putInt("toNextAttack", toNextAttack)
        editor?.putInt("randomLeft", randomLeft)
        editor?.putInt("randomRight", randomRight)
        editor?.putInt("playerShieldValue", playerShieldValue)
        editor?.putInt("actualFlowerType", actualFlowerType)
        editor?.putInt("atkPrice", atkPrice)
        editor?.putInt("healPrice", healPrice)
        editor?.putInt("magicPrice", magicPrice)
        editor?.putInt("shieldPrice", shieldPrice)
        editor?.putInt("atkStatValue", atkStatValue)
        editor?.putInt("healStatValue", healStatValue)
        editor?.putInt("magicStatValue", magicStatValue)
        editor?.putInt("shieldStatValue", shieldStatValue)
        editor?.putBoolean("rollEnemy", rollEnemy)
        editor?.putBoolean("isAutoHarvestOn", isAutoHarvestOn)
        editor?.putBoolean("isAutoWaterOn", isAutoWaterOn)
        editor?.putBoolean("isAutoPlantOn", isAutoPlantOn)
        editor?.putBoolean("canAutoPlant", canAutoPlant)
        editor?.putBoolean("canAlchemy", canAlchemy)
        editor?.putString("sb1text","$autoLevel/3")
        editor?.putString("sb2text","$increaseGrowLevel/5")
        editor?.putString("sb3text","$reduceGrowTime/5")
        editor?.putInt("increaseHarvest", increaseGrowLevel)
        editor?.putInt("reduceGrowTime", reduceGrowTime)
        editor?.putInt("autoLevel", autoLevel)
        editor?.putBoolean("isHealStatMaxed", isHealStatMaxed)
        editor?.putBoolean("isShieldStatMaxed", isShieldStatMaxed)
        editor?.putBoolean("playMusic", playMusic)


        editor?.apply()
    }

    fun loadGame(pref: SharedPreferences?){
        for (i in 0..sizeOfFlowersList){
            flowersCount.add(0)
            canPlant.add(false)
            isSkillBought.add(false)
            flowersCount[i] = pref?.getInt("flower$i",0)!!
            canPlant[i] = pref?.getBoolean("can$i",false)!!
            isSkillBought[i] = pref?.getBoolean("isbought$i",false)!!
        }

        level = pref?.getInt("level",1)!!
        gold = pref?.getInt("gold",0)!!
        actualPlayerHealth = pref?.getInt("actualPlayerHP",20)!!
        maxPlayerHealth = pref?.getInt("maxPlayerHP",20)!!
        toNextAttack = pref?.getInt("toNextAttack",3)!!
        randomLeft = pref?.getInt("randomLeft",0)!!
        randomRight = pref?.getInt("randomRight",0)!!
        playerShieldValue = pref?.getInt("playerShieldValue",0)!!
        actualFlowerType = pref?.getInt("actualFlowerType",0)!!
        atkPrice = pref?.getInt("atkPrice",40)!!
        healPrice = pref?.getInt("healPrice",35)!!
        magicPrice = pref?.getInt("magicPrice",45)!!
        shieldPrice = pref?.getInt("shieldPrice",60)!!
        atkStatValue = pref?.getInt("atkStatValue",4)!!
        healStatValue = pref?.getInt("healStatValue",3)!!
        magicStatValue = pref?.getInt("magicStatValue",6)!!
        shieldStatValue = pref?.getInt("shieldStatValue",2)!!
        rollEnemy = pref?.getBoolean("rollEnemy",true)!!
        isAutoHarvestOn = pref?.getBoolean("isAutoHarvestOn",false)!!
        isAutoWaterOn = pref?.getBoolean("isAutoWaterOn",false)!!
        isAutoPlantOn = pref?.getBoolean("isAutoPlantOn",false)!!
        canAutoPlant = pref?.getBoolean("canAutoPlant",false)!!
        canAlchemy = pref?.getBoolean("canAlchemy",false)
        increaseGrowLevel = pref?.getInt("increaseHarvest",0)!!
        reduceGrowTime = pref?.getInt("reduceGrowTime",0)!!
        autoLevel = pref?.getInt("autoLevel",0)!!
        isHealStatMaxed = pref?.getBoolean("isHealStatMaxed",false)!!
        isShieldStatMaxed = pref?.getBoolean("isShieldStatMaxed",false)!!
        playMusic = pref?.getBoolean("playMusic",true)!!


    }

    fun resetProgress(pref: SharedPreferences?){
        val editor = pref?.edit()
        for (i in 0..MyVariables.sizeOfFlowersList){
            editor?.putInt("flower$i", 0)
            editor?.putBoolean("can$i", false)
            editor?.putBoolean("isbought$i", false)
        }

        editor?.putInt("level", 1)
        editor?.putInt("gold", 0)
        editor?.putInt("actualPlayerHP", 20)
        editor?.putInt("maxPlayerHP", 20)
        editor?.putInt("toNextAttack", 3)
        editor?.putInt("randomLeft", 0)
        editor?.putInt("randomRight", 0)
        editor?.putInt("playerShieldValue", 0)
        editor?.putInt("actualFlowerType", 0)
        editor?.putInt("atkPrice", 40)
        editor?.putInt("healPrice", 35)
        editor?.putInt("magicPrice", 45)
        editor?.putInt("shieldPrice", 60)
        editor?.putInt("atkStatValue", 4)
        editor?.putInt("healStatValue", 3)
        editor?.putInt("magicStatValue", 6)
        editor?.putInt("shieldStatValue", 2)
        editor?.putBoolean("rollEnemy", true)
        editor?.putBoolean("isAutoHarvestOn", false)
        editor?.putBoolean("isAutoWaterOn", false)
        editor?.putBoolean("isAutoPlantOn", false)
        editor?.putBoolean("canAutoPlant", false)
        editor?.putBoolean("canAlchemy", false)
        editor?.putString("sb1text","0/3")
        editor?.putString("sb2text","0/5")
        editor?.putString("sb3text","0/5")
        editor?.putInt("increaseHarvest", 0)
        editor?.putInt("reduceGrowTime", 0)
        editor?.putInt("autoLevel", 0)
        editor?.putBoolean("isHealStatMaxed", false)
        editor?.putBoolean("isShieldStatMaxed", false)

        editor?.apply()
    }

}


class HarvestActivity : AppCompatActivity() {
    var pref: SharedPreferences? = null

    var st1: ImageView?  = null
    var st2a: ImageView? = null
    var st2b: ImageView? = null
    var st3a: ImageView? = null
    var st3b: ImageView? = null
    var st3c: ImageView? = null
    var st4: ImageView?  = null
    var sb1: ImageView?  = null
    var sb2: ImageView?  = null
    var sb3: ImageView?  = null
    var sb4: ImageView?  = null
    var sb1text: TextView? = null
    var sb2text: TextView? = null
    var sb3text: TextView? = null

    var buySkill: ImageView? = null

    var actualPrice1: Int = 0
    var actualPrice2: Int = 0
    var actualPrice3: Int = 0
    var actualPriceIcon1: ImageView? = null
    var actualPriceText1: TextView?  = null
    var actualPriceIcon2: ImageView? = null
    var actualPriceText2: TextView?  = null
    var actualPriceIcon3: ImageView? = null
    var actualPriceText3: TextView?  = null
    var actualSkillDescription: TextView? = null
    var actualSkillIcon: ImageView? = null

    var plant1: ImageView? = null
    var plant2: ImageView? = null
    var plant3: ImageView? = null
    var plant4: ImageView? = null
    var plant5: ImageView? = null
    var plant6: ImageView? = null
    var plant7: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_harvest)

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        pref = getSharedPreferences("my_shared_preferences", MODE_PRIVATE)
        loadGame(pref)

        val plant: ImageView = findViewById(R.id.plant)
        val harvest: ImageView = findViewById(R.id.harvest)
        val water: ImageView = findViewById(R.id.water)

        var isPlantOn = false
        var isHarvestOn = false
        var isWateringOn = false

        var actualAction: ImageView = findViewById(R.id.actualAction)

        if (isAutoPlantOn){
            plant.setImageResource(R.drawable.autoplanton)
        } else if (!isPlantOn && canAutoPlant){
            plant.setImageResource(R.drawable.autoplantoff)
        } else {
            plant.setImageResource(R.drawable.shovel)
        }

        if (isAutoHarvestOn){
            harvest.setImageResource(R.drawable.autoharvest)
        } else {
            harvest.setImageResource(R.drawable.harvest)
        }

        if (isAutoWaterOn){
            water.setImageResource(R.drawable.autowater)
        } else {
            water.setImageResource(R.drawable.water)
        }

        val pickPlant: ConstraintLayout = findViewById(R.id.pickPlant)
        val plantsInPossession: ConstraintLayout = findViewById(R.id.plantsInPossession)
        val showPlantsInPossession: ImageView = findViewById(R.id.showPlantsInPossesion)
        plant1 = findViewById(R.id.plant1)
        plant2 = findViewById(R.id.plant2)
        plant3 = findViewById(R.id.plant3)
        plant4 = findViewById(R.id.plant4)
        plant5 = findViewById(R.id.plant6)
        plant6 = findViewById(R.id.plant5)
        plant7 = findViewById(R.id.plant7)

        var toPlant: PlantName = PlantName.EMPTY

        plant1?.setOnClickListener {
            toPlant = PlantName.DAISY
            pickPlant.isVisible=false
        }
        plant2?.setOnClickListener {
            toPlant = PlantName.ROSE
            pickPlant.isVisible=false
        }
        plant3?.setOnClickListener {
            toPlant = PlantName.SUNFLOWER
            pickPlant.isVisible=false
        }
        plant4?.setOnClickListener {
            toPlant = PlantName.TULIPS
            pickPlant.isVisible=false
        }
        plant5?.setOnClickListener {
            toPlant = PlantName.WATERGRASS
            pickPlant.isVisible=false
        }
        plant6?.setOnClickListener {
            toPlant = PlantName.MUSHROOM
            pickPlant.isVisible=false
        }
        plant7?.setOnClickListener {
            toPlant = PlantName.CENTAUREA
            pickPlant.isVisible=false
        }

        showPlantsInPossession.setOnClickListener {
            if (plantsInPossession.isVisible){
                plantsInPossession.isVisible = false
            } else {
                plantsInPossession.isVisible=true
                plantsInPossession.bringToFront()
            }
        }

        fun manageActualAction(){
            if (isPlantOn ){
//                if (isAutoPlantOn){
//                    actualAction.setImageResource(R.drawable.autoplanton)
//                }else if (!isAutoPlantOn && canAutoPlant) {
//                    actualAction.setImageResource(R.drawable.autoplantoff)
//                } else {
//                    actualAction.setImageResource(R.drawable.shovel)
//                }
                for (i in 0..sizeOfFlowersList){
                    if (canPlant[i]){
                        pickPlant.isVisible = true
                        pickPlant.bringToFront()
                    }
                }
            } else if (isWateringOn){
//                if (isAutoWaterOn){
//                    actualAction.setImageResource(R.drawable.autowater)
//                } else {
//                    actualAction.setImageResource(R.drawable.water)
//                }
                pickPlant.isVisible = false
            } else if (isHarvestOn){
//                if (isAutoHarvestOn){
//                    actualAction.setImageResource(R.drawable.autoharvest)
//                } else {
//                    actualAction.setImageResource(R.drawable.harvest)
//                }
                pickPlant.isVisible = false
            } else {
//                actualAction.setImageResource(R.drawable.base)
                pickPlant.isVisible = false
            }
        }


        plant.setOnClickListener{
            if (isPlantOn){
                isPlantOn=false
            } else {
                isPlantOn=true
                isHarvestOn=false
                isWateringOn=false
            }
            manageActualAction()
        }

        water.setOnClickListener{
            if (isWateringOn){
                isWateringOn=false
            } else {
                isWateringOn=true
                isHarvestOn=false
                isPlantOn=false
            }
            manageActualAction()
        }

        harvest.setOnClickListener {
            if (isHarvestOn){
                isHarvestOn=false
            } else {
                isHarvestOn=true
                isPlantOn=false
                isWateringOn=false
            }
            manageActualAction()
        }

        val fieldsList: ArrayList<ImageView> = ArrayList()
        fun fillFields(){
            fieldsList.add(findViewById(R.id.field1))
            fieldsList.add(findViewById(R.id.field2))
            fieldsList.add(findViewById(R.id.field3))
            fieldsList.add(findViewById(R.id.field4))
            fieldsList.add(findViewById(R.id.field5))
            fieldsList.add(findViewById(R.id.field6))
            fieldsList.add(findViewById(R.id.field7))
            fieldsList.add(findViewById(R.id.field8))
            fieldsList.add(findViewById(R.id.field9))
            fieldsList.add(findViewById(R.id.field10))
            fieldsList.add(findViewById(R.id.field11))
            fieldsList.add(findViewById(R.id.field12))
            fieldsList.add(findViewById(R.id.field13))
            fieldsList.add(findViewById(R.id.field14))
            fieldsList.add(findViewById(R.id.field15))
            fieldsList.add(findViewById(R.id.field16))
            fieldsList.add(findViewById(R.id.field17))
            fieldsList.add(findViewById(R.id.field18))
            fieldsList.add(findViewById(R.id.field19))
            fieldsList.add(findViewById(R.id.field20))
            fieldsList.add(findViewById(R.id.field21))
            fieldsList.add(findViewById(R.id.field22))
            fieldsList.add(findViewById(R.id.field23))
            fieldsList.add(findViewById(R.id.field24))
            fieldsList.add(findViewById(R.id.field25))
            fieldsList.add(findViewById(R.id.field26))
            fieldsList.add(findViewById(R.id.field27))
            fieldsList.add(findViewById(R.id.field28))
            fieldsList.add(findViewById(R.id.field29))
            fieldsList.add(findViewById(R.id.field30))
        }
        fillFields()

        val plantsList: ArrayList<Plants> = ArrayList()
        val fields: ArrayList<Fields> = ArrayList()

        for (i in 0..29){
            plantsList.add(Plants(PlantName.EMPTY,null))
            val field: Fields = Fields(fieldsList.get(i),plantsList.get(i))
            fields.add(field)
            field.field.setImageResource(R.drawable.emptyground)
            field.field.setTag(-69, R.drawable.emptyground)
            field.field.setOnClickListener{
                if (isPlantOn && field.plant.actualCycle==GrowCycle.NONE && toPlant!=PlantName.EMPTY){
                    when(toPlant){
                        PlantName.DAISY -> field.plant = Plants(PlantName.DAISY,field.field)
                        PlantName.ROSE -> field.plant = Plants(PlantName.ROSE,field.field)
                        PlantName.SUNFLOWER -> field.plant = Plants(PlantName.SUNFLOWER,field.field)
                        PlantName.TULIPS -> field.plant = Plants(PlantName.TULIPS,field.field)
                        PlantName.WATERGRASS -> field.plant = Plants(PlantName.WATERGRASS,field.field)
                        PlantName.MUSHROOM -> field.plant = Plants(PlantName.MUSHROOM,field.field)
                        PlantName.CENTAUREA -> field.plant = Plants(PlantName.CENTAUREA,field.field)
                        else -> {}
                    }
                    field.plant.plant()
                } else if (isWateringOn){
                    field.plant.water()
                } else if (isHarvestOn){
                    field.plant.harvest()
                }
            }
        }

        val mainPane: ConstraintLayout = findViewById(R.id.harvestMainPane)
        val skillTree: ConstraintLayout = findViewById(R.id.harvestSkillTree)
        val backFromSkillTree: ImageView = findViewById(R.id.backFromHarvestSkillTree)

        actualAction.setOnClickListener {
            if (mainPane.isVisible){
                mainPane.isVisible=false
                skillTree.isVisible=true
            }
        }

        backFromSkillTree.setOnClickListener {
            if (skillTree.isVisible){
                mainPane.isVisible=true
                skillTree.isVisible=false
            }
        }

        st1 = findViewById(R.id.harvestSkillTier1)
        st2a = findViewById(R.id.harvestSkillTier2a)
        st2b= findViewById(R.id.harvestSkillTier2b)
        st3a = findViewById(R.id.harvestSkillTier3a)
        st3b = findViewById(R.id.harvestSkillTier3b)
        st3c = findViewById(R.id.harvestSkillTier3c)
        st4 = findViewById(R.id.harvestSkillTier4)
        sb1 = findViewById(R.id.harvestSkillBonus1)
        sb2 = findViewById(R.id.harvestSkillBonus2)
        sb3 = findViewById(R.id.harvestSkillBonus3)
        sb4 = findViewById(R.id.harvestSkillBonus4)

        sb1text = findViewById(R.id.harvestSkillBonus1Level)
        sb2text = findViewById(R.id.harvestSkillBonus2Level)
        sb3text = findViewById(R.id.harvestSkillBonus3Level)

        sb1text?.text = pref?.getString("sb1text","0/0")!!
        sb2text?.text = pref?.getString("sb2text","0/0")!!
        sb3text?.text = pref?.getString("sb3text","0/0")!!

        if (isAutoWaterOn){
            sb1?.setImageResource(R.drawable.autoplanton)
        } else if (isAutoHarvestOn){
            sb1?.setImageResource(R.drawable.autowater)
        } else {
            sb1?.setImageResource(R.drawable.autoharvest)
        }

        sb2?.setImageResource(R.drawable.seedsplus)
        sb3?.setImageResource(R.drawable.seedstime)
        sb4?.setImageResource(R.drawable.alchemy)


        buySkill = findViewById(R.id.buyHarvestSkill)

        actualPrice1 = 0
        actualPriceIcon1 = findViewById(R.id.selectedSkillPriceIcon1)
        actualPriceText1 = findViewById(R.id.selectedSkillPriceText1)
        actualPriceIcon2 = findViewById(R.id.selectedSkillPriceIcon2)
        actualPriceText2 = findViewById(R.id.selectedSkillPriceText2)
        actualPriceIcon3 = findViewById(R.id.selectedSkillPriceIcon3)
        actualPriceText3 = findViewById(R.id.selectedSkillPriceText3)
        actualSkillDescription = findViewById(R.id.selectedSkillDescription)
        actualSkillIcon = findViewById(R.id.selectedSkillImage)


        fun showTrash(){
            actualSkillDescription?.textSize = 18F
            buySkill?.isVisible=true
            actualPriceIcon1?.isVisible = true
            actualPriceText1?.isVisible = true
            actualPriceText1?.text = "$actualPrice1"
            if (actualPrice2>0){
                actualPriceIcon2?.isVisible = true
                actualPriceText2?.isVisible = true
                actualPriceText2?.text = "$actualPrice2"
            }else {
                actualPriceIcon2?.isVisible = false
                actualPriceText2?.isVisible = false
            }
            if (actualPrice3>0){
                actualPriceIcon3?.isVisible = true
                actualPriceText3?.isVisible = true
                actualPriceText3?.text = "$actualPrice3"
            }else {
                actualPriceIcon3?.isVisible = false
                actualPriceText3?.isVisible = false
            }
        }

        if (!isAutoHarvestOn){
            sb1?.setOnClickListener {
                actualPrice1=100
                actualPrice2=0
                actualPrice3=0
                actualPriceIcon1?.setImageResource(R.drawable.justgold2)
                selectedFlowerSkillToBuy = MyVariables.flowerSkillType.AUTO_HARVEST
                actualSkillIcon?.setImageResource(R.drawable.autoharvest)
                actualSkillDescription?.text = "Auto-harvest fully grown plants every 5 seconds"
                showTrash()
            }
        } else if (isAutoHarvestOn && !isAutoWaterOn){
            sb1?.setOnClickListener {
                actualPrice1=150
                actualPrice2=0
                actualPrice3=0
                actualPriceIcon1?.setImageResource(R.drawable.justgold2)
                selectedFlowerSkillToBuy = MyVariables.flowerSkillType.AUTO_WATER
                actualSkillIcon?.setImageResource(R.drawable.autowater)
                actualSkillDescription?.text = "Seeds you plant are always watered"
                showTrash()
            }
        } else if (isAutoHarvestOn && isAutoWaterOn && !canAutoPlant){
            sb1?.setOnClickListener {
                actualPrice1=200
                actualPrice2=0
                actualPrice3=0
                actualPriceIcon1?.setImageResource(R.drawable.justgold2)
                selectedFlowerSkillToBuy = MyVariables.flowerSkillType.AUTO_PLANT
                actualSkillIcon?.setImageResource(R.drawable.autoplanton)
                actualSkillDescription?.text = "Allows you to auto-plant selected type of seeds"
                showTrash()
            }
        } else {
            sb1?.setOnClickListener {
                hideTrash()
                actualSkillIcon?.setImageResource(R.drawable.autoplanton)
            }
        }

        if (increaseGrowLevel<5){
            sb2?.setOnClickListener {
                actualPrice1=1+ increaseGrowLevel*10
                actualPrice2=0
                actualPrice3=0
                actualPriceIcon1?.setImageResource(R.drawable.justgold2)
                selectedFlowerSkillToBuy = MyVariables.flowerSkillType.INCREASE_GROW_AMOUNT
                actualSkillIcon?.setImageResource(R.drawable.seedsplus)
                actualSkillDescription?.text = "Increase harvest amount of watered plants (max lvl: 5 giving 7 plant/harvest)"
                showTrash()
            }
        } else {
            sb2?.setOnClickListener {
                hideTrash()
                actualSkillIcon?.setImageResource(R.drawable.seedsplus)
            }
        }

        if (reduceGrowTime<5){
            sb3?.setOnClickListener {
                actualPrice1=1 + reduceGrowTime*15
                actualPrice2=0
                actualPrice3=0
                actualPriceIcon1?.setImageResource(R.drawable.justgold2)
                selectedFlowerSkillToBuy = MyVariables.flowerSkillType.REDUCE_GROW_TIME
                actualSkillIcon?.setImageResource(R.drawable.seedstime)
                actualSkillDescription?.text = "Reduces time needed to full growth by 10% for each upgrade (max 50%)"
                showTrash()
            }
        } else {
            sb3?.setOnClickListener {
                hideTrash()
                actualSkillIcon?.setImageResource(R.drawable.seedstime)
            }
        }

        if (!canAlchemy){
            sb4?.setOnClickListener {
                actualPrice1=100
                actualPrice2=0
                actualPrice3=0
                actualPriceIcon1?.setImageResource(R.drawable.justchabr)
                selectedFlowerSkillToBuy = MyVariables.flowerSkillType.UNLOCK_ALCHEMY
                actualSkillIcon?.setImageResource(R.drawable.alchemy)
                actualSkillDescription?.text = "Unlocks alchemy."
                showTrash()
            }
        } else {
            sb4?.setOnClickListener {
                hideTrash()
                actualSkillIcon?.setImageResource(R.drawable.alchemy)
            }
        }

        if (!canPlant[0]){
            st1?.setOnClickListener {
                actualPrice1=5
                actualPrice2=0
                actualPrice3=0
                actualPriceIcon1?.setImageResource(R.drawable.atk)
                selectedFlowerSkillToBuy = MyVariables.flowerSkillType.DAISY
                actualSkillIcon?.setImageResource(R.drawable.daisy)
                actualSkillDescription?.text = "Allows you to start your journey with gardening by unlocking seeds of Daisy. \nRequires level 5"
                showTrash()
            }
        }

        if (!canPlant[1]) {

            st2a?.setOnClickListener {
                actualPrice1 = 50
                actualPrice2 = 0
                actualPrice3 = 0
                actualPriceIcon1?.setImageResource(R.drawable.justdaisy)
                selectedFlowerSkillToBuy = MyVariables.flowerSkillType.ROSE
                actualSkillIcon?.setImageResource(R.drawable.rose)
                actualSkillDescription?.text = "Unlocks roses. More valuable than sunflowers, but they grow longer"
                showTrash()
            }
        }

        if (!canPlant[2]) {
            st2b?.setOnClickListener {
                actualPrice1 = 35
                actualPrice2 = 0
                actualPrice3 = 0
                actualPriceIcon1?.setImageResource(R.drawable.justdaisy)
                selectedFlowerSkillToBuy = MyVariables.flowerSkillType.SUNFLOWER
                actualSkillIcon?.setImageResource(R.drawable.sunflower)
                actualSkillDescription?.text = "Unlocks sunflower. Grows very fast but generate really low income"
                showTrash()
            }
        }

        if (!canPlant[3]) {
            st3a?.setOnClickListener {
                actualPrice1 = 55
                actualPrice2 = 0
                actualPrice3 = 0
                actualPriceIcon1?.setImageResource(R.drawable.justrose)
                selectedFlowerSkillToBuy = MyVariables.flowerSkillType.TULIP
                actualSkillIcon?.setImageResource(R.drawable.tulips)
                actualSkillDescription?.text = "Unlocks tulips. Their high value comes from their beauty and usability as a gifts"
                showTrash()
            }
        }

        if (!canPlant[4]) {
            st3b?.setOnClickListener {
                actualPrice1 = 100
                actualPrice2 = 100
                actualPrice3 = 0
                actualPriceIcon1?.setImageResource(R.drawable.justrose)
                actualPriceIcon2?.setImageResource(R.drawable.justsunflower)
                selectedFlowerSkillToBuy = MyVariables.flowerSkillType.WATERGRASS
                actualSkillIcon?.setImageResource(R.drawable.watergrass)
                actualSkillDescription?.text = "Unlocks watergrass. Be careful and dont forget to watter it. Requires 100 of each tier 2 plant"
                showTrash()
            }
        }


        if (!canPlant[5]) {
            st3c?.setOnClickListener {
                actualPrice1 = 250
                actualPrice2 = 0
                actualPrice3 = 0
                actualPriceIcon1?.setImageResource(R.drawable.justsunflower)
                selectedFlowerSkillToBuy = MyVariables.flowerSkillType.MUSHROOM
                actualSkillIcon?.setImageResource(R.drawable.mushrom)
                actualSkillDescription?.text = "Unlocks mushroms. Fast growing and delicious for soup."
                showTrash()
            }
        }

        if (!canPlant[6]) {
            st4?.setOnClickListener {
                actualPrice1 = 100
                actualPrice2 = 100
                actualPrice3 = 100
                actualPriceIcon1?.setImageResource(R.drawable.justwatergrass)
                actualPriceIcon2?.setImageResource(R.drawable.justmushroom)
                actualPriceIcon3?.setImageResource(R.drawable.justtulip)
                selectedFlowerSkillToBuy = MyVariables.flowerSkillType.CENTAUREA
                actualSkillIcon?.setImageResource(R.drawable.chabr)
                actualSkillDescription?.text = "Unlocks centaurea's. Most valuable and prettiest flowers of all. Requires 100 of each tier 3 plant"
                showTrash()
            }
        }

        buySkill?.setOnClickListener {
            when(selectedFlowerSkillToBuy){
                MyVariables.flowerSkillType.DAISY -> {
                    if (level >= actualPrice1) {
                        plant1?.isVisible = true
                        canPlant[0] = true
                        isSkillBought[0] = true
                        hideTrash()
                        st1?.setOnClickListener {
                            hideTrash()
                            actualSkillIcon?.setImageResource(R.drawable.daisy)
                        }
                    }
                }MyVariables.flowerSkillType.ROSE -> {
                    if (flowersCount[0]>=actualPrice1){
                        flowersCount[0]-=actualPrice1
                        plant2?.isVisible=true
                        canPlant[1]=true
                        isSkillBought[1]=true
                        hideTrash()
                        st2a?.setOnClickListener {
                            hideTrash()
                            actualSkillIcon?.setImageResource(R.drawable.rose)
                        }
                    }
                }MyVariables.flowerSkillType.SUNFLOWER -> {
                    if (MyVariables.flowersCount[0]>=actualPrice1){
                        flowersCount[0]-=actualPrice1
                        plant3?.isVisible=true
                        canPlant[2]=true
                        isSkillBought[2]=true
                        hideTrash()
                        st2b?.setOnClickListener {
                            hideTrash()
                            actualSkillIcon?.setImageResource(R.drawable.sunflower)
                        }
                    }
                } MyVariables.flowerSkillType.TULIP -> {
                    if (MyVariables.flowersCount[1]>=actualPrice1){
                        flowersCount[1]-=actualPrice1
                        plant4?.isVisible=true
                        canPlant[3]=true
                        isSkillBought[3]=true
                        hideTrash()
                        st3a?.setOnClickListener {
                            hideTrash()
                            actualSkillIcon?.setImageResource(R.drawable.tulips)
                        }
                    }
                } MyVariables.flowerSkillType.WATERGRASS -> {
                    if (MyVariables.flowersCount[1]>=actualPrice1 && MyVariables.flowersCount[2]>=actualPrice1){
                        flowersCount[1]-=actualPrice1
                        flowersCount[2]-=actualPrice1
                        plant5?.isVisible=true
                        canPlant[4]=true
                        isSkillBought[4]=true
                        hideTrash()
                        st3b?.setOnClickListener {
                            hideTrash()
                            actualSkillIcon?.setImageResource(R.drawable.watergrass)
                        }
                    }
                } MyVariables.flowerSkillType.MUSHROOM -> {
                    if (MyVariables.flowersCount[2]>=actualPrice1){
                        flowersCount[2]-=actualPrice1
                        plant6?.isVisible=true
                        canPlant[5]=true
                        isSkillBought[5]=true
                        hideTrash()
                        st3c?.setOnClickListener {
                            hideTrash()
                            actualSkillIcon?.setImageResource(R.drawable.mushrom)
                        }
                    }
                } MyVariables.flowerSkillType.CENTAUREA -> {
                    if (MyVariables.flowersCount[3]>=actualPrice1 && MyVariables.flowersCount[4]>=actualPrice1 && MyVariables.flowersCount[5]>=actualPrice1){
                        flowersCount[3]-=actualPrice1
                        flowersCount[4]-=actualPrice1
                        flowersCount[5]-=actualPrice1
                        plant7?.isVisible=true
                        canPlant[6]=true
                        isSkillBought[6]=true
                        hideTrash()
                        st4?.setOnClickListener {
                            hideTrash()
                            actualSkillIcon?.setImageResource(R.drawable.chabr)
                        }
                    }
                } MyVariables.flowerSkillType.AUTO_HARVEST -> {
                    if (actualPrice1<= gold){
                        gold-=actualPrice1
                        MyVariables.isAutoHarvestOn = true
                        harvest.setImageResource(R.drawable.autoharvest)
                        sb1?.setImageResource(R.drawable.autowater)
                        autoLevel=1
                        sb1text?.text = "$autoLevel/3"
                        showTrash()
                        sb1?.setOnClickListener {
                            actualPrice1=150
                            actualPrice2=0
                            actualPrice3=0
                            actualPriceIcon1?.setImageResource(R.drawable.justgold2)
                            selectedFlowerSkillToBuy = MyVariables.flowerSkillType.AUTO_WATER
                            actualSkillIcon?.setImageResource(R.drawable.autowater)
                            actualSkillDescription?.text = "Seeds you plant are always watered"
                            showTrash()
                        }
                        actualPrice1=150
                        actualPrice2=0
                        actualPrice3=0
                        actualPriceIcon1?.setImageResource(R.drawable.justgold2)
                        selectedFlowerSkillToBuy = MyVariables.flowerSkillType.AUTO_WATER
                        actualSkillIcon?.setImageResource(R.drawable.autowater)
                        actualSkillDescription?.text = "Seeds you plant are always watered"
                        showTrash()
                    }
                } MyVariables.flowerSkillType.AUTO_WATER -> {
                    if (actualPrice1 <=gold){
                        gold-=actualPrice1
                        MyVariables.isAutoWaterOn = true
                        water.setImageResource(R.drawable.autowater)
                        sb1?.setImageResource(R.drawable.autoplanton)
                        autoLevel=2
                        sb1text?.text = "$autoLevel/3"
                        sb1?.setOnClickListener {
                            actualPrice1=200
                            actualPrice2=0
                            actualPrice3=0
                            actualPriceIcon1?.setImageResource(R.drawable.justgold2)
                            selectedFlowerSkillToBuy = MyVariables.flowerSkillType.AUTO_PLANT
                            actualSkillIcon?.setImageResource(R.drawable.autoplanton)
                            actualSkillDescription?.text = "Allows you to auto-plant selected type of seeds"
                            showTrash()
                        }
                        actualPrice1=200
                        actualPrice2=0
                        actualPrice3=0
                        actualPriceIcon1?.setImageResource(R.drawable.justgold2)
                        selectedFlowerSkillToBuy = MyVariables.flowerSkillType.AUTO_PLANT
                        actualSkillIcon?.setImageResource(R.drawable.autoplanton)
                        actualSkillDescription?.text = "Allows you to auto-plant selected type of seeds"
                        showTrash()
                    }
                }
                MyVariables.flowerSkillType.AUTO_PLANT -> {
                    if (actualPrice1 <= gold) {
                        gold -= actualPrice1
                        plant.setImageResource(R.drawable.autoplantoff)
                        canAutoPlant = true
                        autoLevel = 3
                        sb1text?.text = "$autoLevel/3"
                        // TODO: 11.01.2021 implement this method
                        sb1?.setOnClickListener {
                            hideTrash()
                            actualSkillIcon?.setImageResource(R.drawable.autoplanton)
                        }
                        hideTrash()
                        actualSkillIcon?.setImageResource(R.drawable.autoplanton)

                    }
                } MyVariables.flowerSkillType.INCREASE_GROW_AMOUNT -> {
                    if (actualPrice1<= gold && increaseGrowLevel < 5){
                        gold -= actualPrice1
                        increaseGrowLevel++
                        sb2text?.text = "${increaseGrowLevel}/5"
                        actualPrice1=1+ increaseGrowLevel*10
                        showTrash()
                        if (increaseGrowLevel==5){
                            sb2?.setOnClickListener {
                                hideTrash()
                                actualSkillIcon?.setImageResource(R.drawable.seedsplus)
                            }
                            hideTrash()
                            actualSkillIcon?.setImageResource(R.drawable.seedsplus)
                        }
                    }
                } MyVariables.flowerSkillType.REDUCE_GROW_TIME -> {
                    if (actualPrice1<= gold && reduceGrowTime < 5){
                        gold -= actualPrice1
                        reduceGrowTime++
                        sb3text?.text = "${MyVariables.reduceGrowTime}/5"
                        actualPrice1=1+ reduceGrowTime*10
                        showTrash()
                        if (reduceGrowTime==5){
                            sb3?.setOnClickListener {
                                hideTrash()
                                actualSkillIcon?.setImageResource(R.drawable.seedstime)
                            }
                            actualSkillIcon?.setImageResource(R.drawable.seedstime)
                            hideTrash()
                        }

                    }
                }
                MyVariables.flowerSkillType.UNLOCK_ALCHEMY ->
                    if (actualPrice1 <= flowersCount[6]) {
                        gold -= actualPrice1
                        // TODO: 11.01.2021 implement alchemy
                        canAlchemy = true
                        sb4?.setOnClickListener {
                            hideTrash()
                            actualSkillIcon?.setImageResource(R.drawable.alchemy)
                        }
                        hideTrash()
                        actualSkillIcon?.setImageResource(R.drawable.alchemy)
                    }
            }

        }

        hideTrash();

        if (firstTimeOpenHarvest){
            flowersText.add(findViewById(R.id.daisyCount))
            flowersText.add(findViewById(R.id.roseCount))
            flowersText.add(findViewById(R.id.sunflowerCount))
            flowersText.add(findViewById(R.id.tulipsCount))
            flowersText.add(findViewById(R.id.watergrassCount))
            flowersText.add(findViewById(R.id.mushroomCount))
            flowersText.add(findViewById(R.id.centaureaCount))

            firstTimeOpenHarvest=false
        } else {
            flowersText[0] = findViewById(R.id.daisyCount)
            flowersText[1] = findViewById(R.id.roseCount)
            flowersText[2] = findViewById(R.id.sunflowerCount)
            flowersText[3] = findViewById(R.id.tulipsCount)
            flowersText[4] = findViewById(R.id.watergrassCount)
            flowersText[5] = findViewById(R.id.mushroomCount)
            flowersText[6] = findViewById(R.id.centaureaCount)
        }

    }

    fun hideTrash(){
        actualPriceIcon1?.isVisible = false
        actualPriceText1?.isVisible = false
        actualPriceIcon2?.isVisible = false
        actualPriceText2?.isVisible = false
        actualPriceIcon3?.isVisible = false
        actualPriceText3?.isVisible = false
        actualSkillDescription?.text = "BOUGHT"
        actualSkillDescription?.textSize = 30F
        buySkill?.isVisible=false
    }


    fun refreshFlowers(){
        for (text in 0..sizeOfFlowersList){
            flowersText[text].text = "${flowersCount[text]}"
        }

        if (canPlant[0] && isSkillBought[0]){
            plant1?.isVisible=true
            st1?.setOnClickListener {
                hideTrash()
                actualSkillIcon?.setImageResource(R.drawable.daisy)
            }
        }
        if (canPlant[1] && isSkillBought[1]){
            plant2?.isVisible=true
            st2a?.setOnClickListener {
                hideTrash()
                actualSkillIcon?.setImageResource(R.drawable.rose)
            }
        }
        if ( canPlant[2] && isSkillBought[2] ){
            plant3?.isVisible=true
            st2b?.setOnClickListener {
                hideTrash()
                actualSkillIcon?.setImageResource(R.drawable.sunflower)
            }
        }
        if ( canPlant[3] && isSkillBought[3] ){
            plant4?.isVisible=true
            st3a?.setOnClickListener {
                hideTrash()
                actualSkillIcon?.setImageResource(R.drawable.tulips)
            }
        }
        if ( canPlant[4] && isSkillBought[4] ){
            plant5?.isVisible=true
            st3b?.setOnClickListener {
                hideTrash()
                actualSkillIcon?.setImageResource(R.drawable.watergrass)
            }
        }
        if ( canPlant[5] && isSkillBought[5] ){
            plant6?.isVisible=true
            st3c?.setOnClickListener {
                hideTrash()
                actualSkillIcon?.setImageResource(R.drawable.mushrom)
            }
        }
        if ( canPlant[6] && isSkillBought[6] ){
            plant7?.isVisible=true
            st4?.setOnClickListener {
                hideTrash()
                actualSkillIcon?.setImageResource(R.drawable.chabr)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        loadGame(pref)
        sb1text?.text = pref?.getString("sb1text","0/0")
        sb2text?.text = pref?.getString("sb2text","0/0")
        sb3text?.text = pref?.getString("sb3text","0/0")


        refreshFlowers()
    }

    override fun onPause() {
        super.onPause()
        saveGame(pref)
    }
}