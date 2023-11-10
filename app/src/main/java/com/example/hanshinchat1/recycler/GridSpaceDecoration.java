package com.example.hanshinchat1.recycler;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpaceDecoration extends RecyclerView.ItemDecoration {


    private int spanCount;
    private int headerSpace; // 첫 번째 행 위쪽 여백
    private int itemSpace; // 아이템 간 간격

    public GridSpaceDecoration() {
        super();
    }

    public GridSpaceDecoration(int spanCount, int headerSpace, int itemSpace) {
        this.spanCount = spanCount;
        this.headerSpace = headerSpace;
        this.itemSpace = itemSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view); // 아이템 위치
        int column = position % spanCount; // 아이템의 열 위치

        if (position < spanCount) {
            outRect.top = headerSpace;// 첫 번째 행의 상단 여백 설정
        } else {
            outRect.top = itemSpace;
        }
        if (column == 0) {
            // 첫 번째 열에 왼쪽 마진 추가
            outRect.left = itemSpace;
            //outRect.right = itemSpace / 2;
        } else if (column == spanCount - 1) {
            // 마지막 열에 오른쪽 마진 추가
            //outRect.left = itemSpace/ 2;
            outRect.right = itemSpace;
        }
    }

}
