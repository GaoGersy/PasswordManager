package com.gersion.superlock;

import android.support.annotation.FloatRange;
import android.view.View;

import com.yarolegovich.discretescrollview.transform.DiscreteScrollItemTransformer;
import com.yarolegovich.discretescrollview.transform.Pivot;

/**
 * Created by aa326 on 2018/1/13.
 */

public class ItemTransformer implements DiscreteScrollItemTransformer {

    private Pivot pivotX;
    private Pivot pivotY;
    private float minScale;
    private float maxMinDiff;
    private float degree;

    public ItemTransformer() {
        pivotX = Pivot.X.CENTER.create();
        pivotY = Pivot.Y.CENTER.create();
        minScale = 0.8f;
        maxMinDiff = 0.2f;
        degree = 45f;
    }

    @Override
    public void transformItem(View view, float position) {
        pivotX.setOn(view);
        pivotY.setOn(view);
        float closenessToCenter = 1f - Math.abs(position);
        float scale = minScale + maxMinDiff * closenessToCenter;
        view.setScaleX(scale);
        view.setScaleY(scale);
        view.setPivotX(position < 0f ? view.getWidth() : 0f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setRotationY(degree * position);
    }

    public static class Builder {

        private ItemTransformer transformer;
        private float maxScale;

        public Builder() {
            transformer = new ItemTransformer();
            maxScale = 1f;
        }

        public Builder setMinScale(@FloatRange(from = 0.01) float scale) {
            transformer.minScale = scale;
            return this;
        }

        public Builder setMaxScale(@FloatRange(from = 0.01) float scale) {
            maxScale = scale;
            return this;
        }

        public Builder setDegree(float degree) {
            transformer.degree = degree;
            return this;
        }

        public Builder setPivotX(Pivot.X pivotX) {
            return setPivotX(pivotX.create());
        }

        public Builder setPivotX(Pivot pivot) {
            assertAxis(pivot, Pivot.AXIS_X);
            transformer.pivotX = pivot;
            return this;
        }


        public Builder setPivotY(Pivot.Y pivotY) {
            return setPivotY(pivotY.create());
        }

        public Builder setPivotY(Pivot pivot) {
            assertAxis(pivot, Pivot.AXIS_Y);
            transformer.pivotY = pivot;
            return this;
        }

        public ItemTransformer build() {
            transformer.maxMinDiff = maxScale - transformer.minScale;
            return transformer;
        }

        private void assertAxis(Pivot pivot, @Pivot.Axis int axis) {
            if (pivot.getAxis() != axis) {
                throw new IllegalArgumentException("You passed a Pivot for wrong axis.");
            }
        }
    }
}
