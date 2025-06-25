//
//  FavouritesMoviesViewModelWrapper.swift
//  iosApp
//
//  Created by Tal Zion on 25/06/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import Shared

extension FavouritesMoviesScreen {
    
    @MainActor
    class FavouritesMoviesViewModelWrapper: ObservableObject {
        
        let viewModel: FavouritesMoviesViewModel
        @Published var uiState: FavouritesMoviesState
        
        init() {
            self.viewModel = KoinKt.favouritesMoviesViewModel()
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
