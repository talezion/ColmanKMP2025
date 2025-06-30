//
//  ViewModelWrapper.swift
//  iosApp
//
//  Created by Tal Zion on 30/06/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import Shared

@MainActor
class ViewModelWrapper<UiState, ViewModel: BaseViewModel<UiState>>: ObservableObject {

    let viewModel: ViewModel
    @Published var uiState: UiState

    init() {
        self.viewModel = get()
        self.uiState = viewModel.uiState.value
    }

    func startObserving() {
        Task {
            for await state in viewModel.uiState {
                self.uiState = state
            }
        }
    }
}
