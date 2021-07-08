import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEmployee, Employee } from '../employee.model';
import { EmployeeService } from '../service/employee.service';
import { IManager } from 'app/entities/manager/manager.model';
import { ManagerService } from 'app/entities/manager/service/manager.service';

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;

  managersSharedCollection: IManager[] = [];

  editForm = this.fb.group({
    id: [],
    employeeId: [],
    employeeName: [null, [Validators.required, Validators.maxLength(50)]],
    employeeSpecialization: [],
    manager: [],
  });

  constructor(
    protected employeeService: EmployeeService,
    protected managerService: ManagerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  trackManagerById(index: number, item: IManager): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
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

  protected updateForm(employee: IEmployee): void {
    this.editForm.patchValue({
      id: employee.id,
      employeeId: employee.employeeId,
      employeeName: employee.employeeName,
      employeeSpecialization: employee.employeeSpecialization,
      manager: employee.manager,
    });

    this.managersSharedCollection = this.managerService.addManagerToCollectionIfMissing(this.managersSharedCollection, employee.manager);
  }

  protected loadRelationshipsOptions(): void {
    this.managerService
      .query()
      .pipe(map((res: HttpResponse<IManager[]>) => res.body ?? []))
      .pipe(
        map((managers: IManager[]) => this.managerService.addManagerToCollectionIfMissing(managers, this.editForm.get('manager')!.value))
      )
      .subscribe((managers: IManager[]) => (this.managersSharedCollection = managers));
  }

  protected createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id'])!.value,
      employeeId: this.editForm.get(['employeeId'])!.value,
      employeeName: this.editForm.get(['employeeName'])!.value,
      employeeSpecialization: this.editForm.get(['employeeSpecialization'])!.value,
      manager: this.editForm.get(['manager'])!.value,
    };
  }
}
