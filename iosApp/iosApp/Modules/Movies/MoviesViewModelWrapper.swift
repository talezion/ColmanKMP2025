//
//  MoviesViewModelWrapper.swift
//  iosApp
//
//  Created by Tal Zion on 21/05/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import Shared

extension MoviesScreen {
    
    @MainActor
    class MoviesViewModelWrapper: ObservableObject {
        
        let viewModel: MoviesViewModel
        @Published var uiState: MoviesState
        
        init() {
            self.viewModel = ViewModelFactory().createViewModel()
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
    
}
