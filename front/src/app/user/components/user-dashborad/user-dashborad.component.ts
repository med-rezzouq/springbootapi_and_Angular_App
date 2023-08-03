import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NzButtonSize } from 'ng-zorro-antd/button';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';
import { UserService } from '../../user-services/user.service';
import { AdminService } from 'src/app/admin/admin-services/admin.service';

@Component({
  selector: 'app-user-dashborad',
  templateUrl: './user-dashborad.component.html',
  styleUrls: ['./user-dashborad.component.scss']
})
export class UserDashboradComponent implements OnInit {

  products: any = [];
  validateForm!: FormGroup;
  isSpinning = false;
  size: NzButtonSize = 'large';

  constructor(private adminService: AdminService,
    private userService: UserService,
    private notification: NzNotificationService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      title: [null, [Validators.required]],
    });
    this.getAllProducts();
  }

  submitForm() {
    this.isSpinning = true;
    this.products = [];
    if (this.validateForm.get(['title'])!.value) {
      this.adminService.getProductsByTitle(this.validateForm.get(['title'])!.value).subscribe((res) => {
        res.forEach(element => {
          element.processedImg = 'data:image/jpeg;base64,' + element.returnedImg;
          this.products.push(element);
          this.isSpinning = false;
        });
      });
    } else {
      this.getAllProducts();
    }
  }

  getAllUsers() {
    this.isSpinning = true;
    this.userService.getAllUsers().subscribe((res) => {
      console.log(res);
      this.isSpinning = false;
    });
  }

  getAllProducts() {
    this.isSpinning = true;
    this.products = [];
    this.adminService.getAllProducts().subscribe((res) => {
      console.log(res)
      res.forEach(element => {
        element.processedImg = 'data:image/jpeg;base64,' + element.returnedImg;
        this.products.push(element);
        this.isSpinning = false;
      });
    });
  }

  addToCart(productId: any) {
    this.userService.addToCart(productId).subscribe((res) => {
      this.notification
        .success(
          'SUCCESS',
          `Produit ajouté au panier avec succès!`,
          { nzDuration: 5000 }
        );
    });
  }

}
