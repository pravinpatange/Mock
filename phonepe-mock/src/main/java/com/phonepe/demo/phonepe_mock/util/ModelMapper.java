package com.phonepe.demo.phonepe_mock.util;

import com.phonepe.demo.phonepe_mock.dto.BankAccountDto;
import com.phonepe.demo.phonepe_mock.dto.TransactionDto;
import com.phonepe.demo.phonepe_mock.dto.UserDto;
import com.phonepe.demo.phonepe_mock.dto.UserProfileResponse;
import com.phonepe.demo.phonepe_mock.dto.MerchantDto;
import com.phonepe.demo.phonepe_mock.entity.BankAccount;
import com.phonepe.demo.phonepe_mock.entity.Transaction;
import com.phonepe.demo.phonepe_mock.entity.User;
import com.phonepe.demo.phonepe_mock.entity.Merchant;

public class ModelMapper {

    public static UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getActive()
        );
    }

    public static UserProfileResponse toUserProfileResponse(User user) {
        if (user == null) {
            return null;
        }
        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getActive(),
                user.getKycStatus().toString(),
                user.getCreatedAt()
        );
    }

    // ADDED: Missing MerchantDto method
    public static MerchantDto toMerchantDto(Merchant merchant) {
        if (merchant == null) {
            return null;
        }
        return new MerchantDto(
                merchant.getId(),
                merchant.getName(),
                merchant.getUpiId(),
                merchant.getAccountNo(),
                merchant.getContact()
        );
    }

    public static BankAccountDto toBankAccountDto(BankAccount account) {
        if (account == null) {
            return null;
        }
        return new BankAccountDto(
                account.getId(),
                account.getAccountNumber(),
                account.getBankName(),
                account.getIfscCode(),
                account.getBalance(),
                account.getActive()
        );
    }

    public static TransactionDto toTransactionDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        return new TransactionDto(
                transaction.getId(),
                transaction.getTransactionId(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getStatus(),
                transaction.getType(),
                transaction.getSender() != null ?
                        transaction.getSender().getFirstName() + " " + transaction.getSender().getLastName() : null,
                transaction.getReceiver() != null ?
                        transaction.getReceiver().getFirstName() + " " + transaction.getReceiver().getLastName() : null,
                transaction.getCreatedAt()
        );
    }
}
