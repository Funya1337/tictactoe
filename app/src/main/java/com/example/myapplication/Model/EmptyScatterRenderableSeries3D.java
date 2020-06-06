package com.example.myapplication.Model;

import android.content.Intent;
import android.util.Log;

import com.example.myapplication.Activities.WinnerActivity;
import com.example.myapplication.Activities.WinnerActivity3D;
import com.scichart.charting3d.model.dataSeries.xyz.XyzDataSeries3D;
import com.scichart.charting3d.visuals.renderableSeries.hitTest.HitTestInfo3D;
import com.scichart.charting3d.visuals.renderableSeries.scatter.ScatterRenderableSeries3D;

public class EmptyScatterRenderableSeries3D extends ScatterRenderableSeries3D {

    XyzDataSeries3D<Double, Double, Double> emptyDataSeries;
    XyzDataSeries3D<Double, Double, Double> firstPlayerDataSeries;
    XyzDataSeries3D<Double, Double, Double> secondPlayerDataSeries;
    Game game = new Game();
    private static final String TAG = "MainTagName";
    private double x, y, z;
    private boolean checker = true;

    public EmptyScatterRenderableSeries3D(XyzDataSeries3D<Double, Double, Double> emptyDataSeries, XyzDataSeries3D<Double, Double, Double> firstPlayerDataSeries, XyzDataSeries3D<Double, Double, Double> secondPlayerDataSeries) {
        this.emptyDataSeries = emptyDataSeries;
        this.firstPlayerDataSeries = firstPlayerDataSeries;
        this.secondPlayerDataSeries = secondPlayerDataSeries;
    }

    @Override
    public void performSelection(HitTestInfo3D hitTestInfo3D) {
        super.performSelection(hitTestInfo3D);
        checker = !checker;
        int number = (int)asBase3(hitTestInfo3D.vertexId);
        String str = "";
        int length = String.valueOf(number).length();
        if (length == 1)
            str += ("00" + number);
        else if (length == 2)
            str += ("0" + number);
        else
            str += (number + "");
        x = Integer.parseInt(String.valueOf(str.charAt(0)));
        y = Integer.parseInt(String.valueOf(str.charAt(1)));
        z = Integer.parseInt(String.valueOf(str.charAt(2)));
        emptyDataSeries.updateXAt(hitTestInfo3D.vertexId, 10000d);
        if (checker) {
            firstPlayerDataSeries.append(x, y, z);
            game.setElement((int)x, (int)y, (int)z, ElState.X);
        } else {
            secondPlayerDataSeries.append(x, y, z);
            game.setElement((int)x, (int)y, (int)z, ElState.O);
        }
        Log.d(TAG, (int)x + " " + (int)y + " " + (int)z);
        if (game.checkCol1() == ElState.X || game.checkCol2() == ElState.X || game.checkRow1() == ElState.X ||
                game.checkRow2() == ElState.X || game.checkDiag1() == ElState.X || game.checkDiag2() == ElState.X
                || game.checkDiag3() == ElState.X || game.checkDiag4() == ElState.X) {
            Intent intent = new Intent(getContext(), WinnerActivity3D.class);
            intent.putExtra("data", 1);
            getContext().startActivity(intent);
        }
        else if (game.checkCol1() == ElState.O|| game.checkCol2() == ElState.O || game.checkRow1() == ElState.O ||
                game.checkRow2() == ElState.O || game.checkDiag1() == ElState.O || game.checkDiag2() == ElState.O
                || game.checkDiag3() == ElState.O || game.checkDiag4() == ElState.O) {
            Intent intent = new Intent(getContext(), WinnerActivity3D.class);
            intent.putExtra("data", 0);
            getContext().startActivity(intent);
        }
    }
    public long asBase3(int num) {
        long ret = 0, factor = 1;
        while (num > 0) {
            ret += num % 3 * factor;
            num /= 3;
            factor *= 10;
        }
        return ret;
    }
}
