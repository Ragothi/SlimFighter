package com.example.slimfighter

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.solver.widgets.Barrier.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.example.slimfighter.MyVariables.actualEnemy
import com.example.slimfighter.MyVariables.actualFlowerType
import com.example.slimfighter.MyVariables.actualPlayerHealth
import com.example.slimfighter.MyVariables.atkPrice
import com.example.slimfighter.MyVariables.atkStatValue
import com.example.slimfighter.MyVariables.firstRunMain
import com.example.slimfighter.MyVariables.flowersCount
import com.example.slimfighter.MyVariables.healPrice
import com.example.slimfighter.MyVariables.healStatValue
import com.example.slimfighter.MyVariables.increaseGrowLevel
import com.example.slimfighter.MyVariables.isHealStatMaxed
import com.example.slimfighter.MyVariables.isShieldStatMaxed
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
import com.example.slimfighter.MyVariables.shieldPrice
import com.example.slimfighter.MyVariables.shieldStatValue
import com.example.slimfighter.MyVariables.sizeOfFlowersList
import com.example.slimfighter.MyVariables.toNextAttack
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    val attack: PlayerAction = PlayerAction(ActionType.ATTACK,4,R.drawable.attack)
    val defend: PlayerAction = PlayerAction(ActionType.DEFEND,2,R.drawable.shield)
    val heal: PlayerAction = PlayerAction(ActionType.HEAL,3,R.drawable.heal)
    val magic: PlayerAction = PlayerAction(ActionType.MAGIC,6,R.drawable.magic)
    var pref: SharedPreferences? = null
    var flowersIcons: ArrayList<Int> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pref = getSharedPreferences("my_shared_preferences", MODE_PRIVATE)
        loadGame(pref)

        val enemyImage: ImageView =  findViewById(R.id.enemy)
        val enemyHealth: TextView = findViewById(R.id.enemyHealth)
        val enemyName: TextView = findViewById(R.id.enemyName)
        val playerHealth: TextView = findViewById(R.id.playerHealth)
        val playerShield: TextView = findViewById(R.id.playerShield)
        val leftButton: ImageView = findViewById(R.id.actionLeft)
        val rightButton: ImageView = findViewById(R.id.actionRight)
        val level: TextView = findViewById(R.id.level)
        val nextEnemyAttack: TextView = findViewById(R.id.toNextEnemyAttack)
        val enemyAttack: TextView = findViewById(R.id.enemyATK)
        val enemyAttackType: ImageView = findViewById(R.id.enemyAttackType)
        val gold: TextView = findViewById(R.id.goldAmount)
        val shop: ImageView = findViewById(R.id.shop)
        val fightLayout: ConstraintLayout = findViewById(R.id.fightLayout)
        val shopLayout: ConstraintLayout = findViewById(R.id.shopLayout)
        val closeShop: ImageView = findViewById(R.id.closeShop)
        val buyAtkText: TextView = findViewById(R.id.buyAtkText)
        val buyHealText: TextView = findViewById(R.id.buyHealText)
        val buyMagicText: TextView = findViewById(R.id.buyMagicText)
        val buyShieldText: TextView = findViewById(R.id.buyShieldText)
        val buyHealIcon: ImageView = findViewById(R.id.buyHealIcon)
        val buyAtkBack: ImageView = findViewById(R.id.buyAtkBack)
        val buyHealBack: ImageView = findViewById(R.id.buyHealBack)
        val buyMagicBack: ImageView = findViewById(R.id.buyMagicBack)
        val buyShieldBack: ImageView = findViewById(R.id.buyShieldBack)
        val atkStatText: TextView = findViewById(R.id.atkStatText)
        val healStatText: TextView = findViewById(R.id.healStatText)
        val magicStatText: TextView = findViewById(R.id.magicStatText)
        val shieldStatText: TextView = findViewById(R.id.shieldStatText)
        val statsContainer: ConstraintLayout = findViewById(R.id.statsContainer)

        val physicalSlim: Enemy = Enemy("Green Slime",Type.PHYSICAL,12,4,R.drawable.slime,EnemyLevel.ONE)
        val magicalSlim: Enemy = Enemy("Blue Slime",Type.MAGICAL,15,5,R.drawable.magicslime,EnemyLevel.TWO)
        val angel: Enemy = Enemy("Angel",Type.MAGICAL,22,7,R.drawable.angel,EnemyLevel.THREE)
        val viper: Enemy = Enemy("Viper",Type.PHYSICAL,11,8,R.drawable.viper,EnemyLevel.ONE)
        val panda: Enemy = Enemy("Panda",Type.PHYSICAL,35,3,R.drawable.panda,EnemyLevel.TWO)
        val dragonfly: Enemy = Enemy("Dragonfly",Type.PHYSICAL,8,12,R.drawable.dragonfly,EnemyLevel.TWO)
        val monkey: Enemy = Enemy("Monkey",Type.PHYSICAL,19,5,R.drawable.monkey,EnemyLevel.TWO)
        val crock: Enemy = Enemy("Crock",Type.PHYSICAL,15,6,R.drawable.crock,EnemyLevel.TWO)
        val octopus: Enemy = Enemy("Octopus",Type.PHYSICAL,30,5,R.drawable.octopus,EnemyLevel.THREE)
        val karo: Enemy = Enemy("Karo",Type.MAGICAL,150,10,R.drawable.karo,EnemyLevel.FOUR)

        val healthFront: ImageView = findViewById(R.id.healthbarFront)
        val healthBack: ImageView = findViewById(R.id.healthbarBackground)

        val bitmap: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.progressbarhealth)
        val bmd: BitmapDrawable = BitmapDrawable(resources,bitmap)
        val clip: ClipDrawable = ClipDrawable(bmd, Gravity.START,1)
        healthFront.setImageDrawable(clip)
        healthFront.bringToFront()
        playerHealth.bringToFront()

        fun clipHealth(){
            clip.level = (actualPlayerHealth * 10000)/maxPlayerHealth
        }
        clipHealth()

        val enemies: ArrayList<Enemy> = ArrayList()
        val actions: ArrayList<PlayerAction> = ArrayList()
        val buttons: ArrayList<ImageView> = ArrayList()

        enemies.add(physicalSlim)
        enemies.add(magicalSlim)
        enemies.add(angel)
        enemies.add(viper)
        enemies.add(panda)
        enemies.add(octopus)
        enemies.add(monkey)
        enemies.add(crock)
        enemies.add(dragonfly)

        actions.add(attack)
        actions.add(defend)
        actions.add(heal)
        actions.add(magic)

        buttons.add(leftButton)
        buttons.add(rightButton)


        if (rollEnemy){
            actualEnemy = enemies.random()
            if  (MyVariables.level==MyVariables.bossLevel){
                actualEnemy=karo
            }
            actualEnemy.reRollValue()
            toNextAttack = 3
            rollEnemy = false
            randomLeft = actions.random().icon
            randomRight = actions.random().icon
        }

        flowersIcons.add(R.drawable.justdaisy)
        flowersIcons.add(R.drawable.justrose)
        flowersIcons.add(R.drawable.justsunflower)
        flowersIcons.add(R.drawable.justtulip)
        flowersIcons.add(R.drawable.justwatergrass)
        flowersIcons.add(R.drawable.justmushroom)
        flowersIcons.add(R.drawable.justchabr)

        buyAtkText.text = "${atkPrice}"
        buyHealText.text = "${healPrice}"
        buyMagicText.text = "${magicPrice}"
        buyShieldText.text = "${shieldPrice}"
        buyHealIcon.setImageResource(flowersIcons[actualFlowerType])

        gold.text = "${MyVariables.gold}"

        leftButton.setImageResource(randomLeft)
        leftButton.setTag(randomLeft)
        rightButton.setImageResource(randomRight)
        rightButton.setTag(randomRight)

        enemyHealth.text = "${actualEnemy.actualHp}/${actualEnemy.maxHp}"
        enemyImage.setImageResource(actualEnemy.enemyImage)
        playerHealth.text = "$actualPlayerHealth/$maxPlayerHealth"
        playerShield.text = "$playerShieldValue"
        enemyName.text = "${actualEnemy.enemyName} lvl ${actualEnemy.getLevel()}"
        level.text = "Level: ${MyVariables.level}"
        nextEnemyAttack.text = "$toNextAttack"
        enemyAttack.text = "${actualEnemy.atk}"

        if (actualEnemy.type.equals(Type.PHYSICAL)){
            enemyAttackType.setImageResource(R.drawable.atk)
        } else {
            enemyAttackType.setImageResource(R.drawable.mgc)
        }

        fun performAttack(){
            if (playerShieldValue>0){
                val halfEnemyAttackValue: Int = actualEnemy.atk.div(2)
                val restOfAttack: Int =  actualEnemy.atk.rem(2)
                var initialEnemyAttack: Int =  actualEnemy.atk.div(2)

                for (atk in 1..halfEnemyAttackValue ) {
                    if (playerShieldValue > 0) {
                        playerShieldValue--
                        initialEnemyAttack--
                    } else {
                        initialEnemyAttack = 2 * initialEnemyAttack + restOfAttack
                        actualPlayerHealth -= initialEnemyAttack
                        break
                    }
                }

            } else {
                actualPlayerHealth-= actualEnemy.atk
            }

            if (actualPlayerHealth>0){
                playerHealth.text = "$actualPlayerHealth/$maxPlayerHealth"
                playerShield.text = "$playerShieldValue"
                clipHealth()
            } else {
                startActivity(Intent(this,YouDiedActivity::class.java))
            }

        }

        val mpSwordHit: MediaPlayer = MediaPlayer.create(this,R.raw.sword)
        val mpMagicHit: MediaPlayer = MediaPlayer.create(this,R.raw.magic)
        val mpShield: MediaPlayer = MediaPlayer.create(this,R.raw.shield)
        val mpHeal: MediaPlayer = MediaPlayer.create(this,R.raw.heal)


        fun animateAttack(button: ImageView,skillImage: Int){
            val magicball: ImageView = ImageView(this)
            magicball.setImageResource(skillImage)
            magicball.id = View.generateViewId()
            magicball.scaleX = 2f
            magicball.scaleY = 2f

            fightLayout.addView(magicball)

            val cs: ConstraintSet = ConstraintSet();
            var action: Int = 0
            var x: Float = 0f
            val y: Float = -1100f
            cs.clone(fightLayout)


            if (button.equals(rightButton)){
                action=rightButton.id
                x=-200f
            } else if (button.equals(leftButton)){
                action=leftButton.id
                x=200f
            }
            cs.connect(magicball.id,ConstraintSet.TOP,action, ConstraintSet.TOP,0)
            cs.connect(magicball.id, ConstraintSet.BOTTOM,action, ConstraintSet.BOTTOM,0)
            cs.connect(magicball.id, ConstraintSet.START,action, ConstraintSet.START,0)
            cs.connect(magicball.id, ConstraintSet.END,action, ConstraintSet.END,0)
            cs.applyTo(fightLayout)

            magicball.isVisible = true
            ObjectAnimator.ofFloat(magicball,"translationY",y).apply {
                duration =500
                start()
            }
            ObjectAnimator.ofFloat(magicball,"translationX",x).apply {
                duration =500
                start()
            }.doOnEnd {
                fightLayout.removeView(magicball)
            }
        }

        fun animateSupport(button: ImageView, skillImage: Int){
            class temp(var x: Float,var y:Float,val img: ImageView){}

            val list: ArrayList<temp> = ArrayList()

            for (i in 0..3){
                list.add(temp(0f,0f,ImageView(this)))
                list[i].img.setImageResource(skillImage)
                list[i].img.id = View.generateViewId()
                list[i].img.scaleX = 2f
                list[i].img.scaleY = 2f
                fightLayout.addView(list[i].img)
            }
            list[0].y = -200f
            list[1].x = 200f
            list[2].y = 200f
            list[3].x = -200f


            val cs: ConstraintSet = ConstraintSet();
            var action: Int = 0
            cs.clone(fightLayout)


            if (button.equals(rightButton)){
                action=rightButton.id
            } else if (button.equals(leftButton)){
                action=leftButton.id
            }

            for (i in 0..3){
                cs.connect(list[i].img.id,ConstraintSet.TOP,action, ConstraintSet.TOP,0)
                cs.connect(list[i].img.id, ConstraintSet.BOTTOM,action, ConstraintSet.BOTTOM,0)
                cs.connect(list[i].img.id, ConstraintSet.START,action, ConstraintSet.START,0)
                cs.connect(list[i].img.id, ConstraintSet.END,action, ConstraintSet.END,0)
                cs.applyTo(fightLayout)
                list[i].img.isVisible = true

                ObjectAnimator.ofFloat(list[i].img,"translationY",list[i].y).apply {
                    duration = 500
                    start()
                }

                ObjectAnimator.ofFloat(list[i].img,"translationX",list[i].x).apply {
                    duration =500
                    start()
                }.doOnEnd {
                    fightLayout.removeView(list[i].img)
                }
            }

        }

        fun listenToButtons(buttons: ArrayList<ImageView> ){
            for (button in buttons){
                button.setOnClickListener{
                    if (button.getTag() == attack.icon){
//                        val mpSwordHit: MediaPlayer = MediaPlayer.create(this,R.raw.sword)
                        if (MyVariables.playMusic) {
                            mpSwordHit.seekTo(0)
                            mpSwordHit.start()
                        }
                        animateAttack(button,R.drawable.slash)
                        actualEnemy.receiveDamage(attack)
                        enemyHealth.text = "${actualEnemy.actualHp}/${actualEnemy.maxHp}"
                    } else if (button.getTag() == magic.icon){
//                        val mpMagicHit: MediaPlayer = MediaPlayer.create(this,R.raw.magic)
                        if (MyVariables.playMusic){
                            mpMagicHit.seekTo(0)
                            mpMagicHit.start()
                        }
                        animateAttack(button,R.drawable.magicball)
                        actualEnemy.receiveDamage(magic)
                        enemyHealth.text = "${actualEnemy.actualHp}/${actualEnemy.maxHp}"
                    } else if (button.getTag() == defend.icon){
//                        val mpShield: MediaPlayer = MediaPlayer.create(this,R.raw.shield)
                        if (MyVariables.playMusic) {
                            mpShield.seekTo(0)
                            mpShield.start()
                        }
                        animateSupport(button,R.drawable.def)
                        playerShieldValue += shieldStatValue
                        playerShield.text = "$playerShieldValue "
                    } else if (button.getTag() == heal.icon){
//                        val mpHeal: MediaPlayer = MediaPlayer.create(this,R.raw.heal)
                        if (MyVariables.playMusic) {
                            mpHeal.seekTo(0)
                            mpHeal.start()
                        }
                        animateSupport(button,R.drawable.hp)
                        actualPlayerHealth += healStatValue
                        if (actualPlayerHealth> maxPlayerHealth){
                            actualPlayerHealth= maxPlayerHealth
                        }
                        playerHealth.text = "$actualPlayerHealth/$maxPlayerHealth"
                        clipHealth()
                    }

                    if (button.equals(buttons.get(0))){
                        randomLeft = actions.random().icon
                        button.setImageResource(randomLeft)
                        button.setTag(randomLeft)
                    } else if (button.equals(buttons.get(1))){
                        randomRight = actions.random().icon
                        button.setImageResource(randomRight)
                        button.setTag(randomRight)
                    }


                    if (actualEnemy.actualHp<=0){
                        actualEnemy.actualHp=actualEnemy.maxHp
                        MyVariables.gold+=actualEnemy.value
                        gold.text = "${MyVariables.gold}"
                        MyVariables.level++
                        if (MyVariables.level%5==0){
                            for (enemy in enemies){
                                enemy.atk ++
                            }
                        }
                        actualEnemy=enemies.random()
                        if  (MyVariables.level==MyVariables.bossLevel){
                            actualEnemy=karo
                        }
                        actualEnemy.reRollValue()
                        enemyAttack.text = "${actualEnemy.atk}"
                        enemyHealth.text = "${actualEnemy.actualHp}/${actualEnemy.maxHp}"
                        enemyName.text = "${actualEnemy.enemyName} lvl ${actualEnemy.getLevel()}"
                        enemyImage.setImageResource(actualEnemy.enemyImage)
                        level.text = "Level: ${MyVariables.level}"
                        toNextAttack=4
                        if (actualEnemy.type.equals(Type.PHYSICAL)){
                            enemyAttackType.setImageResource(R.drawable.atk)
                        } else {
                            enemyAttackType.setImageResource(R.drawable.mgc)
                        }
                    }

                    toNextAttack--
                    if (toNextAttack>0){
                        nextEnemyAttack.text = "$toNextAttack"
                    } else {
                        performAttack();
                        toNextAttack=3
                        nextEnemyAttack.text = "$toNextAttack"
                    }

                }
            }
        }

        listenToButtons(buttons)

        val harvest: ImageView = findViewById(R.id.enterHarvest)
        harvest.setOnClickListener {
            startActivity(Intent(this,HarvestActivity::class.java))
        }

        shop.setOnClickListener {
            fightLayout.isVisible=false
            shopLayout.isVisible=true
        }

        closeShop.setOnClickListener {
            fightLayout.isVisible=true
            shopLayout.isVisible=false
        }

        buyAtkBack.setOnClickListener {
            if (MyVariables.gold>= atkPrice && atkStatValue<8){
                MyVariables.gold-=atkPrice
                atkStatValue++
                atkPrice*=2
                gold.text = "${MyVariables.gold}"
                buyAtkText.text = "${atkPrice}"
                atkStatText.text = "${attack.value}"
                if (atkStatValue==8){
                    atkPrice=1
                    buyAtkText.text = "${atkPrice}"
                    findViewById<ImageView>(R.id.buyAtkIcon).setImageResource(R.drawable.justchabr)
                }
            } else if (atkStatValue>=8 && flowersCount[6]>=atkPrice){
                flowersCount[6]--
                atkStatValue++
                atkStatText.text = "${attack.value}"
            }
        }

        buyHealBack.setOnClickListener {
            if (flowersCount.isNotEmpty() && flowersCount[actualFlowerType]>= healPrice && healStatValue<11){
                MyVariables.flowersCount[actualFlowerType]-= healPrice
                if (healPrice<200){
                    healPrice*=2
                } else {
                    healPrice=40
                    actualFlowerType++
                }
                healStatValue++
                if (actualFlowerType<=sizeOfFlowersList) {
                    buyHealText.text = "${healPrice}"
                    healStatText.text = "${heal.value}"
                    buyHealIcon.setImageResource(flowersIcons[actualFlowerType])
                } else {
                    buyHealIcon.isVisible=false
                    buyHealText.isVisible=false
                }
                if (healStatValue==10){
                    isHealStatMaxed=true
                    buyHealBack.isVisible=false
                    buyHealText.isVisible = false
                    buyHealIcon.isVisible=false
                }
            }
        }

        buyMagicBack.setOnClickListener {
            if (MyVariables.gold>= magicPrice){
                MyVariables.gold-=magicPrice
                magicPrice*=2
                magicStatValue++
                gold.text = "${MyVariables.gold}"
                buyMagicText.text = "${magicPrice}"
                magicStatText.text = "${magic.value}"
            }
        }

        buyShieldBack.setOnClickListener {
            if (MyVariables.gold>= shieldPrice && shieldStatValue<5){
                MyVariables.gold-=shieldPrice
                shieldPrice*=2
                shieldStatValue++
                gold.text = "${MyVariables.gold}"
                buyShieldText.text = "${shieldPrice}"
                shieldStatText.text = "${defend.value}"
                if (shieldStatValue==5){
                    isShieldStatMaxed = true
                    buyShieldBack.isVisible=false
                    buyShieldText.isVisible = false
                    findViewById<ImageView>(R.id.buyShieldIcon).isVisible=false
                }
            }
        }

        atkStatText.text = "${attack.value}"
        healStatText.text = "${heal.value}"
        magicStatText.text = "${magic.value}"
        shieldStatText.text = "${defend.value}"

        healthFront.setOnClickListener {
            statsContainer.isVisible = !statsContainer.isVisible
            if (statsContainer.isVisible){
                atkStatText.text = "$atkStatValue"
                healStatText.text = "$healStatValue"
                magicStatText.text = "$magicStatValue"
                shieldStatText.text = "$shieldStatValue"
            }
        }

        if (isHealStatMaxed){
            buyHealBack.isVisible=false
            buyHealText.isVisible = false
            buyHealIcon.isVisible=false
        }

        if (isShieldStatMaxed){
            buyShieldBack.isVisible=false
            buyShieldText.isVisible = false
            findViewById<ImageView>(R.id.buyShieldIcon).isVisible=false
        }

        findViewById<Button>(R.id.resetProgress).setOnClickListener {
            MyVariables.resetProgress(pref)
            loadGame(pref)
            startActivity(Intent(this,MainActivity::class.java))

        }


    }



    override fun onResume() {
        super.onResume()
        loadGame(pref)
    }

    override fun onPause() {
        super.onPause()
        saveGame(pref)
    }


}