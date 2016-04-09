package com.jude.fishing.module.social;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.fishing.model.SocialModel;
import com.jude.fishing.model.entities.Contact;
import com.jude.utils.JUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.json.JSONArray;
import org.json.JSONObject;

import rx.Observable;

/**
 * Created by heqiang on 2015/10/20.
 */
public class ContactPresenter extends BeamListActivityPresenter<ContactActivity, Contact> {
    @Override
    protected void onCreate(ContactActivity view, Bundle savedState) {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        RxPermissions.getInstance(getView())
                .request(Manifest.permission.READ_CONTACTS)
                .filter(aBoolean -> aBoolean)
                .flatMap(aBoolean -> Observable.just(readContacts(getView())))
                .flatMap(json -> SocialModel.getInstance().getContact(json))
                .doOnError(throwable -> JUtils.Log(throwable.getLocalizedMessage()))
                .unsafeSubscribe(getRefreshSubscriber());
    }

    String readContacts(Context context) {
        //需要查询的内容
        String[] CONTACTOR_ION = new String[]{
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    CONTACTOR_ION, null, null, null);
            if (cursor != null) {
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                JSONArray jsonArray = new JSONArray();
                while (cursor.moveToNext()) {
                    JSONObject jsonObject = new JSONObject();
                    // 获取联系人姓名
                    jsonObject.put("contact", cursor.getString(nameIndex));
                    // 获取联系人手机号 去除" ","-"
                    jsonObject.put("phone", cursor.getString(phoneIndex).replace(" ", "").replace("-", ""));
                    jsonArray.put(jsonObject);
                }
                return jsonArray.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}
