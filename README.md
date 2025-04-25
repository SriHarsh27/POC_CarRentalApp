POC_CarRentalApp
====================================================================================
This is a minimal viable Android application designed for car rental companies to monitor vehicle speed during a rental. If a renter drives over the permitted speed limit, the company gets notified via Firebase.

Project has been developed under the assumptions mentioned below -
1. User already exists in the Firebase firestore
2. Vehicle has already been rented and rental details are available on the Firebase firestore

Features:
1. Fetches user and speed limit details from firestore
2. Retrieves and updates FCM token for notification in case if required
3. Send violation information to firestore which can been later utilized by Firebase function to notify car rental company.

There are three collections created on the firestore to store the sample documents. These are being referenced in the Android application.
- rentals
  - rental_123
    - speed_limit: 80 (number)
    - start_time: April 25, 2025 at 1:40:25 PM UTC+5:30 (timestamp)
    - user_id: "/users/user_123" (string)
    - vehicle_id: "MH12-1234"(string)

- users
  - user_123
    - dob: April 25, 2025 at 12:00:00 AM UTC+5:30 (timestamp)
    - email: "example@example.com" (string)
    - fcm_token: "FCM_TOKEN" (string)
    - name: "Harsh" (string)

- violations
  - hsg14bowOmW6ZIpdGQkY
    - speed: "105" (string)
    - timestamp: April 25, 2025 at 1:52:21 PM UTC+5:30 (timestamp)
    - user_id: "users/user_123" (string)






