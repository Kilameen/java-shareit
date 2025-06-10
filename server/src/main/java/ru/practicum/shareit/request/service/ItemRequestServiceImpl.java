package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
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

        ItemRequest itemRequest = ItemRequestMapper.toItemRequestFromCreateDto(requester,itemRequestCreateDto);
        itemRequest.setRequester(requester);
        log.info("Создан новый запрос от пользователя {}", requester.getName());


        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemRequestDto> getUserRequests(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));
        List<ItemRequest> itemRequestList = itemRequestRepository.findAllByRequesterId(userId);
        log.info("Запросы пользователя {}", userId);
        return itemRequestList.stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemRequestDto> getAllRequests(Long userId, Integer from, Integer size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));

        int page = from > 0 ? from / size : 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by("created").descending());

        List<ItemRequest> allItemRequests = itemRequestRepository.findAll(pageable).getContent();
        log.info("Получение всех запросов");
        return allItemRequests.stream()
                .filter(itemRequest -> !itemRequest.getRequester().getId().equals(userId))
                .map(ItemRequestMapper::toItemRequestDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ItemRequestDto getAllRequestById(Long userId, Long requestId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));
        ItemRequest requestById = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format("Запрос с id: %s не был найден.", requestId)));

        return ItemRequestMapper.toItemRequestDto(requestById);
    }
}
