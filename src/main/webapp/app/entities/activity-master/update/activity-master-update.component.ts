import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IActivityMaster, ActivityMaster } from '../activity-master.model';
import { ActivityMasterService } from '../service/activity-master.service';

@Component({
  selector: 'jhi-activity-master-update',
  templateUrl: './activity-master-update.component.html',
})
export class ActivityMasterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    activityMasterId: [],
    activity: [null, [Validators.required, Validators.maxLength(250)]],
    changeRequestCode: [null, [Validators.required, Validators.maxLength(50)]],
    projectActivityCode: [null, [Validators.required]],
  });

  constructor(
    protected activityMasterService: ActivityMasterService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activityMaster }) => {
      this.updateForm(activityMaster);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activityMaster = this.createFromForm();
    if (activityMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.activityMasterService.update(activityMaster));
    } else {
      this.subscribeToSaveResponse(this.activityMasterService.create(activityMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivityMaster>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(activityMaster: IActivityMaster): void {
    this.editForm.patchValue({
      id: activityMaster.id,
      activityMasterId: activityMaster.activityMasterId,
      activity: activityMaster.activity,
      changeRequestCode: activityMaster.changeRequestCode,
      projectActivityCode: activityMaster.projectActivityCode,
    });
  }

  protected createFromForm(): IActivityMaster {
    return {
      ...new ActivityMaster(),
      id: this.editForm.get(['id'])!.value,
      activityMasterId: this.editForm.get(['activityMasterId'])!.value,
      activity: this.editForm.get(['activity'])!.value,
      changeRequestCode: this.editForm.get(['changeRequestCode'])!.value,
      projectActivityCode: this.editForm.get(['projectActivityCode'])!.value,
    };
  }
}
