package org.firstinspires.ftc.teamcode.subsystem.sensors.filter;


/***
 * 递推平均滤波法（又称滑动平均滤波法）
 * 方法：
 * 把连续取N个采样值看成一个队列
 * 队列的长度固定为N
 * 每次采样到一个新数据放入队尾,并扔掉原来队首的一次数据.(先进先出原则)
 * 把队列中的N个数据进行算术平均运算,就可获得新的滤波结果
 * N值的选取：流量,N=12;压力：N=4;液面,N=4~12;温度,N=1~4
 *
 * 优点：
 * 对周期性干扰有良好的抑制作用,平滑度高
 * 适用于高频振荡的系统
 *
 * 缺点：
 * 灵敏度低
 * 对偶然出现的脉冲性干扰的抑制作用较差
 * 不易消除由于脉冲干扰所引起的采样值偏差
 * 不适用于脉冲干扰比较严重的场合
 * 比较浪费RAM
 */
public class SensorFilterRecurrenceAvg {

    int maxAvgCount = 5;
    int currAvgCount = 0;
    int insertIndex = 0;
    double sum = 0.0;
    double avg = 0.0;

    double[] dataQueue ;

    public SensorFilterRecurrenceAvg(){
        init();
    }

    public SensorFilterRecurrenceAvg(int avgCount){
        maxAvgCount = avgCount;
        init();
    }

    public void init(){
        dataQueue = new double[maxAvgCount];
        insertIndex = 0;
    }

    public double input(double value){
        if(currAvgCount < maxAvgCount){
            currAvgCount++;
        }else{
            sum -= dataQueue[insertIndex-1];
        }
        sum +=value;
        avg = sum / currAvgCount;
        dataQueue[insertIndex-1] = value;

        insertIndex++;
        if(insertIndex>=maxAvgCount){
            insertIndex = 0;
        }

        return avg;
    }

    public double getValue(){
        return avg;
    }

}
