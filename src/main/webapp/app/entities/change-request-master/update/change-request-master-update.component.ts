import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IChangeRequestMaster, ChangeRequestMaster } from '../change-request-master.model';
import { ChangeRequestMasterService } from '../service/change-request-master.service';

@Component({
  selector: 'jhi-change-request-master-update',
  templateUrl: './change-request-master-update.component.html',
})
export class ChangeRequestMasterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    changeRequestId: [],
    changeRequestCode: [null, [Validators.required, Validators.maxLength(50)]],
    projectActivityCode: [null, [Validators.required]],
  });

  constructor(
    protected changeRequestMasterService: ChangeRequestMasterService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ changeRequestMaster }) => {
      this.updateForm(changeRequestMaster);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const changeRequestMaster = this.createFromForm();
    if (changeRequestMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.changeRequestMasterService.update(changeRequestMaster));
    } else {
      this.subscribeToSaveResponse(this.changeRequestMasterService.create(changeRequestMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChangeRequestMaster>>): void {
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

  protected updateForm(changeRequestMaster: IChangeRequestMaster): void {
    this.editForm.patchValue({
      id: changeRequestMaster.id,
      changeRequestId: changeRequestMaster.changeRequestId,
      changeRequestCode: changeRequestMaster.changeRequestCode,
      projectActivityCode: changeRequestMaster.projectActivityCode,
    });
  }

  protected createFromForm(): IChangeRequestMaster {
    return {
      ...new ChangeRequestMaster(),
      id: this.editForm.get(['id'])!.value,
      changeRequestId: this.editForm.get(['changeRequestId'])!.value,
      changeRequestCode: this.editForm.get(['changeRequestCode'])!.value,
      projectActivityCode: this.editForm.get(['projectActivityCode'])!.value,
    };
  }
}
