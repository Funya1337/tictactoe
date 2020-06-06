package com.example.myapplication.Model;

import android.view.MotionEvent;

import com.scichart.charting3d.modifiers.VertexSelectionModifier3D;

public class MyVertexSelectionModifier3D extends VertexSelectionModifier3D {

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        System.out.println("e.getX()");
        System.out.println(e.getX());
        return super.onSingleTapConfirmed(e);
    }
}
