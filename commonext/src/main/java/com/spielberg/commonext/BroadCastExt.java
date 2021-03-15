package com.spielberg.commonext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BroadCastExt {
    private BroadCastExt() {
        throw new RuntimeException("Do not need instantiate!");
    }


    public static LocalBroadcastManager getBroadcastManager(Context ctx) {
        return LocalBroadcastManager.getInstance(ctx);
    }


    public static IntentFilter getIntentFilter(String... actions) {
        IntentFilter filter = null;
        if (actions.length > 0) {
            filter = new IntentFilter();
            for (String action : actions) {
                filter.addAction(action);
            }
        }
        return filter;
    }

    /**
     * @Desc 通过Action注册广播接收者
     * @Param [ctx, receiver, actions]
     */
    public static void registerReceiver(Context ctx, BroadcastReceiver receiver, String... actions) {
        IntentFilter filter = getIntentFilter(actions);
        if (filter != null) {
            registerReceiver(ctx, receiver, filter);
        }
    }

    /**
     * @Desc 通过IntentFilter注册广播接收者
     * @Param [ctx, receiver, filter]
     */
    public static void registerReceiver(Context ctx, BroadcastReceiver receiver, IntentFilter filter) {
        getBroadcastManager(ctx).registerReceiver(receiver, filter);
    }

    /**
     * @Desc 注销广播接收者
     * @Param [ctx, receiver]
     */
    public static void unRegisterReceiver(Context ctx, BroadcastReceiver receiver) {
        getBroadcastManager(ctx).unregisterReceiver(receiver);
    }

    /**
     * @Desc 通过Action发送广播
     * @Param [ctx, action]
     */
    public static void sendBroadcast(Context ctx, String action) {
        sendBroadcast(ctx, new Intent(action));
    }

    /**
     * @Desc 通过intent发送广播
     * @Param [ctx, intent]
     */
    public static void sendBroadcast(Context ctx, Intent intent) {
        getBroadcastManager(ctx).sendBroadcast(intent);
    }

    /**
     * @Desc 通过Action同步发送广播
     * @Param [ctx, action]
     */
    public static void sendBroadcastSync(Context ctx, String action) {
        sendBroadcastSync(ctx, new Intent(action));
    }

    /**
     * @Desc 通过Intent同步发送广播
     * @Param [ctx, intent]
     */
    public static void sendBroadcastSync(Context ctx, Intent intent) {
        getBroadcastManager(ctx).sendBroadcastSync(intent);
    }
    // ChatConst.EXTRA_STOP_CHAT
    public static void sendStopChat(Context ctx, String reason) {
        Intent intent = new Intent(ChatingService.ACTION_STOP);
        intent.putExtra(ChatConst.EXTRA_STOP_CHAT, reason);
        sendBroadcast(ctx, intent);
    }

    //也是挂断，但是携带具体原因参考LLContstant
//    public static void sendStopChat(Context ctx, PushRoomStateBean bean) {
//        Intent intent = new Intent(LLChatingService.ACTION_STOP);
//        intent.putExtra(EXTRA_ACTION_STOP, bean);
//        sendBroadcast(ctx, intent);
//    }

//    public static void sendPushOnlineState(Context ctx, PushRoomStateBean bean) {
//        Logger.i("push_inf0 -> " + bean);
//        if (bean == null) return;
//        if (Utils.isStopChatPush(bean.operate)) {
//            sendStopChat(ctx, bean);
//        } else {
//            Intent intent = new Intent(LLChatingService.ACTION_ROOM_CHANGE);
//            intent.putExtra(EXTRA_ACTION_ONLINE_SATTE, bean);
//            sendBroadcast(ctx, intent);
//        }
//    }
}
