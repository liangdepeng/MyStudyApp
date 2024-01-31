package com.dpjh.batteryinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BatteryMainActivity extends AppCompatActivity {

    private TextView batteryTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_main);
        batteryTv = findViewById(R.id.battery_tv);
        registerBatteryReceiver();
    }

    private void registerBatteryReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }

    private final BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            StringBuilder builder = new StringBuilder();

            int batteryStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 9999);
            builder.append("充电状态：").append(transformStatus(batteryStatus)).append("\n");

            int batteryHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 9999);
            builder.append("健康状况：").append(transformHealth(batteryHealth)).append("\n");

            boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
            builder.append("电池是否存在：").append(present).append("\n");

            int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 9999);
            int maxLevel = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 9999);
            builder.append("电池电量：").append(currentLevel).append("/").append(maxLevel).append("\n");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                boolean lowLevel = intent.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW, false);
                builder.append("电池电量是否过低：").append(lowLevel).append("电量 ").append(currentLevel).append("\n");
            }

            int chargePlugin = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 9999);
            builder.append("充电接口信息：").append(transformPlugin(chargePlugin)).append("\n");

            int voltage1 = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 9999);
            builder.append("电池电压：").append(voltage1).append("V").append("\n");

            int temperature1 = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 9999);
            builder.append("电池温度：").append(temperature1).append("℃").append("\n");

            String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            builder.append("电池技术：").append(technology).append("\n");

            int chargerCode = intent.getIntExtra("invalid_charger", 9999);
            builder.append("充电异常1：").append(chargerCode != 0 ? "设备连接了不受支持的充电器" : "无异常").append("\n");

            BatteryManager batteryManager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                long timeMills = batteryManager.computeChargeTimeRemaining();
                if (timeMills != -1) {
                    builder.append("充电剩余时间：").append(transformTime1(timeMills)).append("\n");
                }
            }

            if (batteryTv != null) {
                batteryTv.setText(builder.toString());
            }
        }
    };

    private String transformTime1(long timeMills) {
        long hourMills = 60 * 60 * 1000;
        long minuteMills = 60 * 1000;
        int hourNum = (int) (timeMills / hourMills);
        int minuteNum = (int) ((timeMills - hourNum * hourMills) / minuteMills);
        int secondNum = (int) ((timeMills - hourNum * hourMills - minuteNum * minuteMills) / 1000);
        return hourMills + "小时" + minuteNum + "分" + secondNum + "秒";
    }

    private String transformPlugin(int chargePlugin) {
        switch (chargePlugin) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                return "交流充电器";
            case BatteryManager.BATTERY_PLUGGED_USB:
                return "USB端口";
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                return "无线充电";
            case 0:
                return "电池供电";
            default:
                return "未知";
        }
    }

    private String transformHealth(int batteryHealth) {
        switch (batteryHealth) {
            case BatteryManager.BATTERY_HEALTH_GOOD:
                return "电池健康良好";
            case BatteryManager.BATTERY_HEALTH_COLD:
                return "电池温度过低";
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                return "电池温度过高";
            case BatteryManager.BATTERY_HEALTH_DEAD:
                return "电池健康过低";
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                return "电池电压过高";
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                return "电池状态异常";
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
            default:
                return "获取健康信息失败";
        }
    }

    private String transformStatus(int statusCode) {
        switch (statusCode) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                return "电池状态充电中";
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                return "电池状态放电中";
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                return "电池状态未充电";
            case BatteryManager.BATTERY_STATUS_FULL:
                return "电池状态已满";
            default:
                return "电池状态未知";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
    }
}