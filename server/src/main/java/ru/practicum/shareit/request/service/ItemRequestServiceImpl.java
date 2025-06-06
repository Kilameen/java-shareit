package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.ItemRequestMapper;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {

    private final UserRepository userRepository;
    private final ItemRequestRepository itemRequestRepository;

    @Override
    public ItemRequestDto create(Long userId, ItemRequestCreateDto itemRequestCreateDto) {
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));
        ItemRequest itemRequest = ItemRequestMapper.toItemRequestFromCreateDto(itemRequestCreateDto);
        itemRequest.setRequester(requester);
        log.info("Создан новый запрос от пользователя {}",requester.getName());
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemRequestDto> getUserRequests(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));
        List<ItemRequest> itemRequestList = itemRequestRepository.findAllByRequesterId(userId);
        log.info("Запросы пользователя {}",userId);
        return itemRequestList.stream().map(ItemRequestMapper::toItemRequestDto).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    @Override
    public List<ItemRequestDto> getAllRequests() {
        List<ItemRequest> allItemRequests = itemRequestRepository.findAllByOrderByCreatedDesc();
        log.info("Получение всех запросов");
        return allItemRequests.stream().map(ItemRequestMapper::toItemRequestDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ItemRequestDto getAllRequestById(Long requestId){
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос с ID " + requestId + " не найден."));
        log.info("Получен запрос по ID {}",requestId);
        return ItemRequestMapper.toItemRequestDto(itemRequest);
    }
}
