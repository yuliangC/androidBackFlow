package cn.ytxu.androidbackflow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2017/1/5.
 */
class BackFlowIntent {
    /**
     * 回退功能的类型（BackFlowType.type）
     * type is int
     */
    private static final String BACK_FLOW_TYPE = "back_flow_type";

    /**
     * 回退功能中指定的activity
     * type is String
     */
    private static final String BACK_FLOW_ACTIVITY = "back_flow_activity";

    /**
     * 回退功能中指定的fragment顺序列
     * type is String
     */
    private static final String BACK_FLOW_FRAGMENTS = "back_flow_fragments";

    /**
     * 回退Activity界面的数量,每一次回退都会--backActivityCount,当currBackActivityCount为0的时候，不再回退
     * 若设置为1，则只finish当前的activity
     * type is int
     */
    private static final String BACK_ACTIVITY_COUNT = "back_activity_count";

    /**
     * 回退功能中用户带入的额外数据
     * type is Bundle
     */
    private static final String BACK_FLOW_EXTRA = "back_flow_extra";


    //**************************** back flow type ****************************
    private static Intent putType(Intent data, int type) {
        return data.putExtra(BACK_FLOW_TYPE, type);
    }

    static int getType(Intent data) {
        return data.getIntExtra(BACK_FLOW_TYPE, BackFlowType.error.getType());
    }

    static boolean isBackFlowType(Intent data) {
        return data != null && data.hasExtra(BACK_FLOW_TYPE);
    }


    //**************************** activity class name ****************************
    private static Intent putActivity(Intent data, @NonNull Class<? extends Activity> atyClass) {
        String activityClassName = BackFlowViewHelper.getActivityClassName(atyClass);
        return data.putExtra(BACK_FLOW_ACTIVITY, activityClassName);
    }

    static String getActivity(Intent data) {
        return data.getStringExtra(BACK_FLOW_ACTIVITY);
    }


    //**************************** fragment class name ****************************
    private static Intent putFragments(Intent data, @NonNull List<Class<? extends Fragment>> fragmentClazzs) {
        JSONArray fragmentClassNameJsonArray = new JSONArray();
        for (Class<? extends Fragment> fragmentClazz : fragmentClazzs) {
            String fragmentClassName = BackFlowViewHelper.getFragmentClassName(fragmentClazz);
            fragmentClassNameJsonArray.put(fragmentClassName);
        }
        return data.putExtra(BACK_FLOW_FRAGMENTS, fragmentClassNameJsonArray.toString());
    }

    static List<String> getFragments(Intent data) {
        String targetFragmentClassNames = data.getStringExtra(BACK_FLOW_FRAGMENTS);
        try {
            JSONArray fragmentClassNameJsonArray = new JSONArray(targetFragmentClassNames);
            List<String> fragmentClassNames = new ArrayList<>(fragmentClassNameJsonArray.length());
            for (int i = 0; i < fragmentClassNameJsonArray.length(); i++) {
                String fragmentClassName = fragmentClassNameJsonArray.getString(i);
                fragmentClassNames.add(fragmentClassName);
            }
            return fragmentClassNames;
        } catch (JSONException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }


    //**************************** activity count ****************************
    static Intent putBackActivityCount(Intent data, int backActivityCount) {
        return data.putExtra(BACK_ACTIVITY_COUNT, backActivityCount);
    }

    static int getBackActivityCount(Intent data, int stopBackActivityCount) {
        return data.getIntExtra(BACK_ACTIVITY_COUNT, stopBackActivityCount);
    }


    //**************************** extra ****************************
    private static Intent putExtra(Intent data, @Nullable Bundle extra) {
        if (extra != null) {
            data.putExtra(BACK_FLOW_EXTRA, extra);
        }
        return data;
    }

    static boolean hasExtra(Intent data) {
        return data.hasExtra(BACK_FLOW_EXTRA);
    }

    static Bundle getExtra(Intent data) {
        return data.getBundleExtra(BACK_FLOW_EXTRA);
    }


    //**************************** builder ****************************
    static final class Builder {
        private final Intent requestData;

        Builder(int type, @Nullable Bundle extra) {
            this.requestData = BackFlowIntent.putExtra(putType(new Intent(), type), extra);
        }

        Builder putActivity(@NonNull Class<? extends Activity> atyClass) {
            BackFlowIntent.putActivity(requestData, atyClass);
            return this;
        }

        Builder putFragments(@NonNull List<Class<? extends Fragment>> fragmentClazzs) {
            BackFlowIntent.putFragments(requestData, fragmentClazzs);
            return this;
        }

        Builder putBackActivityCount(int backActivityCount) {
            BackFlowIntent.putBackActivityCount(requestData, backActivityCount);
            return this;
        }

        Intent get() {
            return requestData;
        }
    }

}
