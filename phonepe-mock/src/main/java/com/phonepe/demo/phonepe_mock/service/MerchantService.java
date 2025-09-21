package com.phonepe.demo.phonepe_mock.service;

import com.phonepe.demo.phonepe_mock.dto.MerchantDto;
import com.phonepe.demo.phonepe_mock.entity.Merchant;
import com.phonepe.demo.phonepe_mock.exception.ResourceNotFoundException;
import com.phonepe.demo.phonepe_mock.exception.UserAlreadyExistsException;
import com.phonepe.demo.phonepe_mock.repository.MerchantRepository;
import com.phonepe.demo.phonepe_mock.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MerchantService {

    private static final Logger log = LoggerFactory.getLogger(MerchantService.class);

    private final MerchantRepository merchantRepository;

    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public List<MerchantDto> getAllMerchants() {
        return merchantRepository.findAll()
                .stream()
                .map(ModelMapper::toMerchantDto)
                .collect(Collectors.toList());
    }

    public Optional<MerchantDto> findByUpiId(String upiId) {
        return merchantRepository.findByUpiId(upiId)
                .map(ModelMapper::toMerchantDto);
    }

    public Optional<MerchantDto> findByAccountNo(String accountNo) {
        return merchantRepository.findByAccountNo(accountNo)
                .map(ModelMapper::toMerchantDto);
    }

    public MerchantDto getMerchantById(Long id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with id: " + id));
        return ModelMapper.toMerchantDto(merchant);
    }

    @Transactional
    public MerchantDto createMerchant(MerchantDto merchantDto) {
        // Check if merchant already exists with UPI ID
        if (merchantRepository.existsByUpiId(merchantDto.getUpiId())) {
            throw new UserAlreadyExistsException("Merchant already exists with UPI ID: " + merchantDto.getUpiId());
        }

        // Check if merchant already exists with Account Number
        if (merchantRepository.existsByAccountNo(merchantDto.getAccountNo())) {
            throw new UserAlreadyExistsException("Merchant already exists with Account Number: " + merchantDto.getAccountNo());
        }

        Merchant merchant = new Merchant();
        merchant.setName(merchantDto.getName());
        merchant.setUpiId(merchantDto.getUpiId());
        merchant.setAccountNo(merchantDto.getAccountNo());
        merchant.setContact(merchantDto.getContact());

        Merchant savedMerchant = merchantRepository.save(merchant);

        log.info("Merchant created: {}", savedMerchant.getName());

        return ModelMapper.toMerchantDto(savedMerchant);
    }

    @Transactional
    public MerchantDto updateMerchant(Long id, MerchantDto merchantDto) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with id: " + id));

        merchant.setName(merchantDto.getName());
        merchant.setContact(merchantDto.getContact());

        Merchant updatedMerchant = merchantRepository.save(merchant);

        log.info("Merchant updated: {}", updatedMerchant.getName());

        return ModelMapper.toMerchantDto(updatedMerchant);
    }

    @Transactional
    public void deleteMerchant(Long id) {
        if (!merchantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Merchant not found with id: " + id);
        }

        merchantRepository.deleteById(id);

        log.info("Merchant deleted with id: {}", id);
    }

    public List<MerchantDto> searchMerchantsByName(String name) {
        return merchantRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ModelMapper::toMerchantDto)
                .collect(Collectors.toList());
    }
}
