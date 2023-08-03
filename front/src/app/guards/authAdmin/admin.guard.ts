import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Observable } from 'rxjs';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private router: Router,
    private notification: NzNotificationService,) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {

    if(UserStorageService.isUserLoggedIn()){
      this.router.navigateByUrl('/user/dashboard');
      this.notification
      .error(
        'ERROR',
        `Vous n'avez pas accès à cette page!!!`,
        { nzDuration: 5000 }
      );
      return false;
    }
    else if(!UserStorageService.hasToken()){
      UserStorageService.signOut();
      this.router.navigateByUrl('/login');
      this.notification
      .error(
        'ERROR',
        `Vous n'êtes pas connecté. S'il vous plait Connectez-vous d'abord!!!`,
        { nzDuration: 5000 }
      );
      return false;
    }
    return true;
  }

}