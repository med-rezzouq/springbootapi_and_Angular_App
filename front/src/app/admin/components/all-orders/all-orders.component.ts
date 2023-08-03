import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { NzButtonSize } from 'ng-zorro-antd/button';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { AdminService } from '../../admin-services/admin.service';

@Component({
  selector: 'app-all-orders',
  templateUrl: './all-orders.component.html',
  styleUrls: ['./all-orders.component.scss']
})
export class AllOrdersComponent implements OnInit {

  validateForm!: FormGroup;
  isSpinning = false;
  size: NzButtonSize = 'large';
  Orders: any;

  constructor(private adminService: AdminService,
    private notification: NzNotificationService,
    private fb: FormBuilder,
    private router: Router) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
    });
    this.getPlacedOrders();
  }

  getPlacedOrders() {
    this.adminService.getPlacedOrders().subscribe((res) => {
      this.Orders = res;
    })
  }

}
