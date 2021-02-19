package com.example.slimfighter

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.slimfighter.MyVariables.actualFlowerType
import com.example.slimfighter.MyVariables.atkPrice
import com.example.slimfighter.MyVariables.atkStatValue
import com.example.slimfighter.MyVariables.healPrice
import com.example.slimfighter.MyVariables.healStatValue
import com.example.slimfighter.MyVariables.magicPrice
import com.example.slimfighter.MyVariables.magicStatValue
import com.example.slimfighter.MyVariables.rollEnemy
import com.example.slimfighter.MyVariables.shieldPrice
import com.example.slimfighter.MyVariables.shieldStatValue

class YouDiedActivity : AppCompatActivity() {

    var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_youdied)


        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        val restart: ImageView = findViewById(R.id.restart)
        restart.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        pref = getSharedPreferences("my_shared_preferences", MODE_PRIVATE)

    }

    override fun onResume() {
        super.onResume()
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