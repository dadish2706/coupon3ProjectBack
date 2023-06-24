package com.Coupons.DayMission;
import com.Coupons.Entities.Coupon;
import com.Coupons.Repository.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class DailyJob implements Runnable {
    /**
     * this is runnable thread class that connecting to the coupon repository
     *
     */
    @Autowired
    CouponRepo couponRepo;

    boolean quit = false;

    /**
     * this method for deleting expired date coupons
     * will make a list of coupons then will collect them if the end date expired
     * and then will delete them by delete all in Batch at once
     * with that will not need to call to the server every coupon
     *
     */
    public void deleteAllExpiredCoupons() {

        List<Coupon> expiredCoupon = couponRepo.findByEndDateLessThanEqual(LocalDate.now());
        System.out.println("Found :"+expiredCoupon+" to delete");
        couponRepo.deleteAllInBatch(expiredCoupon);
        System.out.println("Deleted");

    }


    /**
     * this is run method for thread that will check if
     * there is expired coupons will activate
     * to delete coupons method
     * after will go to sleep for 24 hours then wake up and run again
     *
     */
    @Override
    public void run() {
        System.out.println("======== task started");
        while ((!this.quit)) {
            try {
                System.out.println("check for expired coupons");
                deleteAllExpiredCoupons();
                System.out.println("Nap time wake me up in 24 hours good night 😴");
                TimeUnit.HOURS.sleep(24);
            } catch (InterruptedException e) {
                System.out.println("thread interrupted and will stop");
                break;
            }
            System.out.println(">>> from task");
        }
        System.out.println("======== Daily job-task ended");
    }


    /**
     * while didn't get an order to stop the thread wil run
     */

    /**
     * this for stopping the thread
     */
    public void stop() {
        this.quit = true;
    }
}
