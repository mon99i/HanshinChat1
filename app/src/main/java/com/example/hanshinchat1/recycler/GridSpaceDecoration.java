package com.example.hanshinchat1.recycler;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpaceDecoration extends RecyclerView.ItemDecoration{


    private int spanCount;
   /* private int spacing;
    private boolean includeEdge;*/
    private int headerSpace; // 첫 번째 행 위쪽 여백
    private int itemSpace; // 아이템 간 간격

    public GridSpaceDecoration() {
        super();
    }

    public GridSpaceDecoration(int spanCount, int headerSpace,int itemSpace) {
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
        }else {
            outRect.top=itemSpace;
        }
       /* if (column == 0) {
            //좌측 Spacing 절반
            outRect.right = itemSpace/ 2;
        } else {
            //우측 Spacing 절반
            outRect.left = itemSpace/ 2;
        }*/

/*

        if (position < spanCount) {
            // 첫 번째 행에 있는 아이템일 경우
            outRect.top = headerSpace;
        } else {
            outRect.top = itemSpace; // 다음 행과의 간격 설정
        }

        if (column != 1){
            outRect.left = itemSpace;
        }
*/
        if (column == 0) {
            // 첫 번째 열에 왼쪽 마진 추가
            outRect.left = itemSpace;
            //outRect.right = itemSpace / 2;
        } else if (column == spanCount - 1) {
            // 마지막 열에 오른쪽 마진 추가
            //outRect.left = itemSpace/ 2;
            outRect.right = itemSpace;
        } /*else {
            // 중간 열은 양쪽 마진을 반반 추가
            outRect.left = spacing / 2;
            outRect.right = spacing / 2;
        }

        outRect.left = itemSpace - column * itemSpace / spanCount; // 왼쪽 여백 설정
        outRect.right = (column + 1) * itemSpace / spanCount; // 오른쪽 여백 설정*/

    }



}
