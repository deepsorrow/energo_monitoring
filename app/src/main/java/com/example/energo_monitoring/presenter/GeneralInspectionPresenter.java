package com.example.energo_monitoring.presenter;

import android.content.Context;

import com.example.energo_monitoring.model.api.ClientInfo;
import com.example.energo_monitoring.model.api.OrganizationInfo;
import com.example.energo_monitoring.model.api.ProjectDescription;
import com.example.energo_monitoring.model.db.OtherInfo;
import com.example.energo_monitoring.model.db.ResultData;
import com.example.energo_monitoring.model.db.ResultDataDatabase;
import com.example.energo_monitoring.presenter.utilities.SharedPreferencesManager;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class GeneralInspectionPresenter {

    Context context;

    public GeneralInspectionPresenter(Context context) {
        this.context = context;
    }

    public void insertDataToDb(int dataId, boolean lightIsOk, boolean sanPinIsOk, String comment){
        ResultDataDatabase db = ResultDataDatabase.getDatabase(context);
        Observable.just(db)
                .subscribeOn(Schedulers.io())
                .subscribe((value) -> {
                    ClientInfo clientInfo = db.resultDataDAO().getClientInfo(dataId);
                    OrganizationInfo organizationInfo = db.resultDataDAO().getOrganizationInfo(dataId);
                    ProjectDescription projectDescription = db.resultDataDAO().getProjectDescription(dataId);

                    db.resultDataDAO().deleteOtherInfo(dataId);
                    OtherInfo otherInfo = new OtherInfo(dataId, lightIsOk, sanPinIsOk, comment);
                    otherInfo.clientId = clientInfo.id;
                    otherInfo.organizationId = organizationInfo.id;
                    otherInfo.projectId = projectDescription.id;
                    otherInfo.userId = SharedPreferencesManager.getUserId(context);

                    db.resultDataDAO().insertOtherInfo(otherInfo);
                });
    }
}
