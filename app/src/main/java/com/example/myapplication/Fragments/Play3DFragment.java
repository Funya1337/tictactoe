package com.example.myapplication.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Model.EmptyScatterRenderableSeries3D;
import com.example.myapplication.Model.MyVertexSelectionModifier3D;
import com.example.myapplication.R;
import com.scichart.charting3d.model.dataSeries.xyz.XyzDataSeries3D;
import com.scichart.charting3d.modifiers.ModifierGroup3D;
import com.scichart.charting3d.modifiers.OrbitModifier3D;
import com.scichart.charting3d.modifiers.PinchZoomModifier3D;
import com.scichart.charting3d.modifiers.VertexSelectionModifier3D;
import com.scichart.charting3d.visuals.SciChartSurface3D;
import com.scichart.charting3d.visuals.axes.NumericAxis3D;
import com.scichart.charting3d.visuals.camera.Camera3D;
import com.scichart.charting3d.visuals.pointMarkers.SpherePointMarker3D;
import com.scichart.charting3d.visuals.renderableSeries.metadataProviders.DefaultSelectableMetadataProvider3D;
import com.scichart.charting3d.visuals.renderableSeries.metadataProviders.PointMetadataProvider3D;
import com.scichart.charting3d.visuals.renderableSeries.scatter.ScatterRenderableSeries3D;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.drawing.utility.ColorUtil;
import com.scichart.extensions3d.builders.SciChart3DBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Play3DFragment extends Fragment {
    @BindView(R.id.chart3d)
    SciChartSurface3D surface3d;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.play_3d_fragment, container, false);
        ButterKnife.bind(this, rootView);
        initExample();
        return rootView;
    }
    protected final SciChart3DBuilder sciChart3DBuilder = SciChart3DBuilder.instance();

    protected void initExample() {
        final Camera3D camera = sciChart3DBuilder.newCamera3D().build();
        surface3d.setIsAxisCubeVisible(false);

        final NumericAxis3D xAxis = sciChart3DBuilder.newNumericAxis3D().withGrowBy(.2, .2).build();
        final NumericAxis3D yAxis = sciChart3DBuilder.newNumericAxis3D().withGrowBy(.05, .05).build();
        final NumericAxis3D zAxis = sciChart3DBuilder.newNumericAxis3D().withGrowBy(.2, .2).build();

        final XyzDataSeries3D<Double, Double, Double> emptyDataSeries = new XyzDataSeries3D<>(Double.class, Double.class, Double.class);
        final XyzDataSeries3D<Double, Double, Double> firstPlayerDataSeries = new XyzDataSeries3D<>(Double.class, Double.class, Double.class);
        final XyzDataSeries3D<Double, Double, Double> secondPlayerDataSeries = new XyzDataSeries3D<>(Double.class, Double.class, Double.class);

        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                for (int k=0; k<3; k++) {
                    emptyDataSeries.append((double)i, (double)j, (double)k);
                }
            }
        }

        final SpherePointMarker3D emptyPointMarker = sciChart3DBuilder.newSpherePointMarker3D()
                .withFill(ColorUtil.White)
                .withSize(80f)
                .build();
        final SpherePointMarker3D firstPlayerPointMarker = sciChart3DBuilder.newSpherePointMarker3D()
                .withFill(Color.parseColor("#EDF67D"))
                .withSize(80f)
                .build();
        final SpherePointMarker3D secondPlayerPointMarker = sciChart3DBuilder.newSpherePointMarker3D()
                .withFill(Color.parseColor("#F896D8"))
                .withSize(80f)
                .build();

        EmptyScatterRenderableSeries3D emptyRs = new EmptyScatterRenderableSeries3D(emptyDataSeries, firstPlayerDataSeries, secondPlayerDataSeries);
        ScatterRenderableSeries3D firstPlayerRs = new ScatterRenderableSeries3D();
        ScatterRenderableSeries3D secondPlayerRs = new ScatterRenderableSeries3D();
        emptyRs.setDataSeries(emptyDataSeries);
        firstPlayerRs.setDataSeries(firstPlayerDataSeries);
        secondPlayerRs.setDataSeries(secondPlayerDataSeries);
        emptyRs.setPointMarker(emptyPointMarker);
        firstPlayerRs.setPointMarker(firstPlayerPointMarker);
        secondPlayerRs.setPointMarker(secondPlayerPointMarker);
        emptyRs.setMetadataProvider(new DefaultSelectableMetadataProvider3D());
        UpdateSuspender.using(surface3d, () -> {
            surface3d.setCamera(camera);

            surface3d.setXAxis(xAxis);
            surface3d.setYAxis(yAxis);
            surface3d.setZAxis(zAxis);

            surface3d.getRenderableSeries().add(emptyRs);
            surface3d.getRenderableSeries().add(firstPlayerRs);
            surface3d.getRenderableSeries().add(secondPlayerRs);

            VertexSelectionModifier3D selectModifier = new MyVertexSelectionModifier3D();
            selectModifier.setReceiveHandledEvents(true);
            OrbitModifier3D orbitModifier = new OrbitModifier3D();
            orbitModifier.setReceiveHandledEvents(true);

//            boolean modifier = sciChart3DBuilder.newModifierGroup()
//                    .withVertexSelectionModifier().withReceiveHandledEvents(true).build()
//                    .withPinchZoomModifier3D().build()
//                    .withOrbitModifier3D().withReceiveHandledEvents(true).build()
//                    .withZoomExtentsModifier3D().build()
//                    .build();
            surface3d.getChartModifiers().add(new ModifierGroup3D(selectModifier, orbitModifier));
            surface3d.getChartModifiers().add(new PinchZoomModifier3D());
        });
    }
}
