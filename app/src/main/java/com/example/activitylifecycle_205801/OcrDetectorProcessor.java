package com.example.activitylifecycle_205801;

import android.util.SparseArray;

import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.mlsdk.text.MLText;

public class OcrDetectorProcessor implements MLAnalyzer.MLTransactor<MLText.Block> {
    @Override
    public void transactResult(MLAnalyzer.Result<MLText.Block> results) {
        SparseArray<MLText.Block> items = results.getAnalyseList();
        // 开发者根据需要处理识别结果，需要注意，这里只对检测结果进行处理。
        // 不可调用ML Kit提供的其他检测相关接口。
    }
    @Override
    public void destroy() {
        // 检测结束回调方法，用于释放资源等。
    }
}
