package com.arpangroup.user_service.service.registration;

import com.arpangroup.user_service.dto.UserCreateRequest;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.validation.UserValidator;
import com.arpangroup.user_service.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserValidator validator;

    @Override
    public User registerUser(UserCreateRequest request) throws InvalidRequestException {
        validator.validateRegistrationRequest(request);

        /*
        $referUser = User::where('username', $data['referral'])->first();
        $position = $data['position'];
        $positioner = Mlm::getPositioner($referUser, $position);

         //User Create
        $user            = new User();
        $user->email     = strtolower($data['email']);
        $user->firstname = $data['firstname'];
        $user->lastname  = $data['lastname'];
        $user->password  = Hash::make($data['password']);
        $user->ref_by = $referUser->id;
        $user->pos_id = $positioner->id;
        $user->position = $position;
        $user->kv = gs('kv') ? Status::NO : Status::YES;
        $user->ev = gs('ev') ? Status::NO : Status::YES;
        $user->sv = gs('sv') ? Status::NO : Status::YES;
        $user->ts = Status::DISABLE;
        $user->tv = Status::ENABLE;
        $user->save();

        $adminNotification            = new AdminNotification();
        $adminNotification->user_id   = $user->id;
        $adminNotification->title     = 'New member registered';
        $adminNotification->click_url = urlPath('admin.users.detail', $user->id);
        $adminNotification->save();
         */

        return null;
    }
}
