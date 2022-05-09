package com.example.energo_monitoring.checks.presenters;

import android.content.Context;

import com.example.energo_monitoring.checks.data.api.ClientInfo;
import com.example.energo_monitoring.checks.data.api.OrganizationInfo;
import com.example.energo_monitoring.checks.data.api.ProjectDescription;
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase;
import com.example.energo_monitoring.checks.data.db.OtherInfo;
import com.example.energo_monitoring.checks.presenters.utilities.SharedPreferencesManager;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class GeneralInspectionPresenter {

    Context context;

    public GeneralInspectionPresenter(Context context) {
        this.context = context;
    }

    public void insertDataToDb(int dataId, Boolean lightIsOk, Boolean sanPinIsOk, String comment){
        ResultDataDatabase db = ResultDataDatabase.getDatabase(context);
        Observable.just(db)
                .subscribeOn(Schedulers.io())
                .subscribe((value) -> {
                    ClientInfo clientInfo = db.resultDataDAO().getClientInfo(dataId);
                    OrganizationInfo organizationInfo = db.resultDataDAO().getOrganizationInfo(dataId);
                    ProjectDescription projectDescription = db.resultDataDAO().getProjectDescription(dataId);

                    db.resultDataDAO().deleteOtherInfo(dataId);
                    OtherInfo otherInfo = new OtherInfo(dataId, lightIsOk, sanPinIsOk, comment);
                    otherInfo.currentScreen = 3;
                    otherInfo.clientId = clientInfo.id;
                    otherInfo.organizationId = organizationInfo.id;
                    otherInfo.projectId = projectDescription.id;
                    otherInfo.userId = SharedPreferencesManager.getUserId(context);
                    if (lightIsOk != otherInfo.lightIsOk || sanPinIsOk != otherInfo.sanPinIsOk
                            || !comment.equals(otherInfo.generalInspectionComment)) {
                        otherInfo.localVersion += 1;
                    }

                    db.resultDataDAO().insertOtherInfo(otherInfo);
                });
    }
}
