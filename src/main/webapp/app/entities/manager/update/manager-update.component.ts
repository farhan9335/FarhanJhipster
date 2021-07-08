import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IManager, Manager } from '../manager.model';
import { ManagerService } from '../service/manager.service';

@Component({
  selector: 'jhi-manager-update',
  templateUrl: './manager-update.component.html',
})
export class ManagerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    managerId: [],
    managerName: [null, [Validators.required, Validators.maxLength(50)]],
    level: [null, [Validators.required, Validators.maxLength(10)]],
  });

  constructor(protected managerService: ManagerService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ manager }) => {
      this.updateForm(manager);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const manager = this.createFromForm();
    if (manager.id !== undefined) {
      this.subscribeToSaveResponse(this.managerService.update(manager));
    } else {
      this.subscribeToSaveResponse(this.managerService.create(manager));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IManager>>): void {
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

  protected updateForm(manager: IManager): void {
    this.editForm.patchValue({
      id: manager.id,
      managerId: manager.managerId,
      managerName: manager.managerName,
      level: manager.level,
    });
  }

  protected createFromForm(): IManager {
    return {
      ...new Manager(),
      id: this.editForm.get(['id'])!.value,
      managerId: this.editForm.get(['managerId'])!.value,
      managerName: this.editForm.get(['managerName'])!.value,
      level: this.editForm.get(['level'])!.value,
    };
  }
}
