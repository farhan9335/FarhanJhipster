import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IActivityMaster } from '../activity-master.model';
import { ActivityMasterService } from '../service/activity-master.service';
import { ActivityMasterDeleteDialogComponent } from '../delete/activity-master-delete-dialog.component';

@Component({
  selector: 'jhi-activity-master',
  templateUrl: './activity-master.component.html',
})
export class ActivityMasterComponent implements OnInit {
  activityMasters?: IActivityMaster[];
  isLoading = false;

  constructor(protected activityMasterService: ActivityMasterService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.activityMasterService.query().subscribe(
      (res: HttpResponse<IActivityMaster[]>) => {
        this.isLoading = false;
        this.activityMasters = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IActivityMaster): number {
    return item.id!;
  }

  delete(activityMaster: IActivityMaster): void {
    const modalRef = this.modalService.open(ActivityMasterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.activityMaster = activityMaster;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
