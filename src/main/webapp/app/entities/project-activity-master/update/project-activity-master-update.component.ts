import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IProjectActivityMaster, ProjectActivityMaster } from '../project-activity-master.model';
import { ProjectActivityMasterService } from '../service/project-activity-master.service';

@Component({
  selector: 'jhi-project-activity-master-update',
  templateUrl: './project-activity-master-update.component.html',
})
export class ProjectActivityMasterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    projectActivityId: [],
    projectActivityCode: [null, [Validators.required]],
    description: [null, [Validators.required, Validators.maxLength(50)]],
  });

  constructor(
    protected projectActivityMasterService: ProjectActivityMasterService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectActivityMaster }) => {
      this.updateForm(projectActivityMaster);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectActivityMaster = this.createFromForm();
    if (projectActivityMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.projectActivityMasterService.update(projectActivityMaster));
    } else {
      this.subscribeToSaveResponse(this.projectActivityMasterService.create(projectActivityMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectActivityMaster>>): void {
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

  protected updateForm(projectActivityMaster: IProjectActivityMaster): void {
    this.editForm.patchValue({
      id: projectActivityMaster.id,
      projectActivityId: projectActivityMaster.projectActivityId,
      projectActivityCode: projectActivityMaster.projectActivityCode,
      description: projectActivityMaster.description,
    });
  }

  protected createFromForm(): IProjectActivityMaster {
    return {
      ...new ProjectActivityMaster(),
      id: this.editForm.get(['id'])!.value,
      projectActivityId: this.editForm.get(['projectActivityId'])!.value,
      projectActivityCode: this.editForm.get(['projectActivityCode'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }
}
