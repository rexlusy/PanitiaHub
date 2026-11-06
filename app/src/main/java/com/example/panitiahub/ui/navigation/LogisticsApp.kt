package com.example.panitiahub.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.panitiahub.data.SampleContactData
import com.example.panitiahub.data.SampleData
import com.example.panitiahub.data.SampleExpenseData
import com.example.panitiahub.data.SampleNoticeData
import com.example.panitiahub.data.SampleTaskData
import com.example.panitiahub.ui.screens.BorrowFormScreen
import com.example.panitiahub.ui.screens.ContactDetailScreen
import com.example.panitiahub.ui.screens.ContactFormScreen
import com.example.panitiahub.ui.screens.ContactListScreen
import com.example.panitiahub.ui.screens.EventCodeGateScreen
import com.example.panitiahub.ui.screens.ExpenseDetailScreen
import com.example.panitiahub.ui.screens.ExpenseFormScreen
import com.example.panitiahub.ui.screens.ExpenseListScreen
import com.example.panitiahub.ui.screens.HomeScreen
import com.example.panitiahub.ui.screens.LogisticsDetailScreen
import com.example.panitiahub.ui.screens.LogisticsPagerScreen
import com.example.panitiahub.ui.screens.LoginScreen
import com.example.panitiahub.ui.screens.NoticeBoardScreen
import com.example.panitiahub.ui.screens.NoticeDetailScreen
import com.example.panitiahub.ui.screens.NoticeFormScreen
import com.example.panitiahub.ui.screens.OnboardingScreen
import com.example.panitiahub.ui.screens.RegisterScreen
import com.example.panitiahub.ui.screens.TaskDetailScreen
import com.example.panitiahub.ui.screens.TaskFormScreen
import com.example.panitiahub.ui.screens.TaskListScreen

sealed class Screen(val route: String) {
    // Auth & Onboarding flow
    data object Onboarding : Screen("onboarding")
    data object EventCodeGate : Screen("event_code_gate")
    data object Login : Screen("login")
    data object Register : Screen("register")

    // Home Dashboard Hub
    data object HomeDashboard : Screen("home_dashboard")

    // Modul 1: Contacts
    data object ContactList : Screen("contact_list")
    data object ContactDetail : Screen("contact_detail/{contactId}") {
        fun createRoute(contactId: String) = "contact_detail/$contactId"
    }
    data object ContactForm : Screen("contact_form")

    // Modul 2: Logistics
    data object LogisticsPager : Screen("logistics_pager")
    data object LogisticsDetail : Screen("logistics_detail/{itemId}") {
        fun createRoute(itemId: String) = "logistics_detail/$itemId"
    }
    data object BorrowForm : Screen("borrow_form")

    // Modul 3: Finance / Expense
    data object ExpenseList : Screen("expense_list")
    data object ExpenseDetail : Screen("expense_detail/{expenseId}") {
        fun createRoute(expenseId: String) = "expense_detail/$expenseId"
    }
    data object ExpenseForm : Screen("expense_form")

    // Modul 4: Tasks
    data object TaskList : Screen("task_list")
    data object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(taskId: String) = "task_detail/$taskId"
    }
    data object TaskForm : Screen("task_form")

    // Modul 5: Notice Board
    data object NoticeBoard : Screen("notice_board")
    data object NoticeDetail : Screen("notice_detail/{noticeId}") {
        fun createRoute(noticeId: String) = "notice_detail/$noticeId"
    }
    data object NoticeForm : Screen("notice_form")
}

@Composable
fun LogisticsApp() {
    val navController = rememberNavController()

    var contactList by remember { mutableStateOf(SampleContactData.initialContacts) }
    var itemsList by remember { mutableStateOf(SampleData.initialItems) }
    var expenseList by remember { mutableStateOf(SampleExpenseData.initialExpenses) }
    var taskList by remember { mutableStateOf(SampleTaskData.initialTasks) }
    var noticeList by remember { mutableStateOf(SampleNoticeData.initialNotices) }

    var selectedExpenseToEdit by remember { mutableStateOf<com.example.panitiahub.data.ExpenseItem?>(null) }

    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        // --- AUTH & ONBOARDING ---
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onContinueClick = {
                    navController.navigate(Screen.EventCodeGate.route)
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.EventCodeGate.route) {
            EventCodeGateScreen(
                onBackClick = { navController.popBackStack() },
                onSubmitCode = { _ ->
                    navController.navigate(Screen.HomeDashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onBackClick = { navController.popBackStack() },
                onLoginSuccess = {
                    navController.navigate(Screen.HomeDashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onBackClick = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Screen.HomeDashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        // --- HOME DASHBOARD HUB ---
        composable(Screen.HomeDashboard.route) {
            HomeScreen(
                onNavigateRoute = { route -> navigateBottomBar(navController, route) },
                onNavigateModule = { module ->
                    when (module) {
                        "contacts" -> navController.navigate(Screen.ContactList.route)
                        "logistics" -> navController.navigate(Screen.LogisticsPager.route)
                        "finance" -> navController.navigate(Screen.ExpenseList.route)
                        "tasks" -> navController.navigate(Screen.TaskList.route)
                        "notices" -> navController.navigate(Screen.NoticeBoard.route)
                    }
                }
            )
        }

        // --- MODUL 1: Contacts ---
        composable(Screen.ContactList.route) {
            ContactListScreen(
                contacts = contactList,
                onContactClick = { contact ->
                    navController.navigate(Screen.ContactDetail.createRoute(contact.id))
                },
                onAddContactClick = {
                    navController.navigate(Screen.ContactForm.route)
                },
                onNavigateRoute = { route -> navigateBottomBar(navController, route) }
            )
        }

        composable(
            route = Screen.ContactDetail.route,
            arguments = listOf(navArgument("contactId") { type = NavType.StringType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getString("contactId") ?: ""
            val contact = contactList.find { it.id == contactId } ?: contactList.firstOrNull() ?: SampleContactData.initialContacts[0]

            ContactDetailScreen(
                contact = contact,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.ContactForm.route) {
            ContactFormScreen(
                onBackClick = { navController.popBackStack() },
                onSubmit = { newContact ->
                    contactList = listOf(newContact) + contactList
                    navController.popBackStack()
                }
            )
        }

        // --- MODUL 2: Logistics ---
        composable(Screen.LogisticsPager.route) {
            LogisticsPagerScreen(
                items = itemsList,
                onItemClick = { item ->
                    navController.navigate(Screen.LogisticsDetail.createRoute(item.id))
                },
                onReturnClick = { item ->
                    itemsList = itemsList.map {
                        if (it.id == item.id) it.copy(isReturned = true) else it
                    }
                },
                onAddClick = {
                    navController.navigate(Screen.BorrowForm.route)
                },
                onNavigateRoute = { route -> navigateBottomBar(navController, route) }
            )
        }

        composable(
            route = Screen.LogisticsDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            val item = itemsList.find { it.id == itemId } ?: itemsList.firstOrNull() ?: SampleData.initialItems[0]

            LogisticsDetailScreen(
                item = item,
                onBackClick = { navController.popBackStack() },
                onConfirmReturnClick = {
                    itemsList = itemsList.map {
                        if (it.id == item.id) it.copy(isReturned = true) else it
                    }
                    navController.popBackStack()
                },
                onCancelItemClick = {
                    itemsList = itemsList.filterNot { it.id == item.id }
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.BorrowForm.route) {
            BorrowFormScreen(
                onBackClick = { navController.popBackStack() },
                onSubmit = { newItem ->
                    itemsList = listOf(newItem) + itemsList
                    navController.popBackStack()
                }
            )
        }

        // --- MODUL 3: Finance / Expense ---
        composable(Screen.ExpenseList.route) {
            ExpenseListScreen(
                expenses = expenseList,
                onExpenseClick = { expense ->
                    navController.navigate(Screen.ExpenseDetail.createRoute(expense.id))
                },
                onAddExpenseClick = {
                    selectedExpenseToEdit = null
                    navController.navigate(Screen.ExpenseForm.route)
                },
                onNavigateRoute = { route -> navigateBottomBar(navController, route) }
            )
        }

        composable(
            route = Screen.ExpenseDetail.route,
            arguments = listOf(navArgument("expenseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getString("expenseId") ?: ""
            val expense = expenseList.find { it.id == expenseId } ?: expenseList.firstOrNull() ?: SampleExpenseData.initialExpenses[0]

            ExpenseDetailScreen(
                item = expense,
                onBackClick = { navController.popBackStack() },
                onEditClick = {
                    selectedExpenseToEdit = expense
                    navController.navigate(Screen.ExpenseForm.route)
                },
                onDeleteClick = {
                    expenseList = expenseList.filterNot { it.id == expense.id }
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ExpenseForm.route) {
            ExpenseFormScreen(
                initialItem = selectedExpenseToEdit,
                onBackClick = { navController.popBackStack() },
                onSubmit = { savedExpense ->
                    val existingIndex = expenseList.indexOfFirst { it.id == savedExpense.id }
                    expenseList = if (existingIndex >= 0) {
                        expenseList.toMutableList().apply { set(existingIndex, savedExpense) }
                    } else {
                        listOf(savedExpense) + expenseList
                    }
                    selectedExpenseToEdit = null
                    navController.popBackStack()
                }
            )
        }

        // --- MODUL 4: Tasks ---
        composable(Screen.TaskList.route) {
            TaskListScreen(
                tasks = taskList,
                onTaskClick = { task ->
                    navController.navigate(Screen.TaskDetail.createRoute(task.id))
                },
                onToggleTaskCompleted = { task ->
                    taskList = taskList.map {
                        if (it.id == task.id) it.copy(isCompleted = !it.isCompleted) else it
                    }
                },
                onAddTaskClick = {
                    navController.navigate(Screen.TaskForm.route)
                },
                onNavigateRoute = { route -> navigateBottomBar(navController, route) }
            )
        }

        composable(
            route = Screen.TaskDetail.route,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            val task = taskList.find { it.id == taskId } ?: taskList.firstOrNull() ?: SampleTaskData.initialTasks[0]

            TaskDetailScreen(
                task = task,
                onBackClick = { navController.popBackStack() },
                onToggleCompleted = {
                    taskList = taskList.map {
                        if (it.id == task.id) it.copy(isCompleted = !it.isCompleted) else it
                    }
                },
                onDeleteClick = {
                    taskList = taskList.filterNot { it.id == task.id }
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.TaskForm.route) {
            TaskFormScreen(
                onBackClick = { navController.popBackStack() },
                onSubmit = { newTask ->
                    taskList = listOf(newTask) + taskList
                    navController.popBackStack()
                }
            )
        }

        // --- MODUL 5: Notice Board ---
        composable(Screen.NoticeBoard.route) {
            NoticeBoardScreen(
                notices = noticeList,
                onNoticeClick = { notice ->
                    navController.navigate(Screen.NoticeDetail.createRoute(notice.id))
                },
                onAddNoticeClick = {
                    navController.navigate(Screen.NoticeForm.route)
                },
                onNavigateRoute = { route -> navigateBottomBar(navController, route) }
            )
        }

        composable(
            route = Screen.NoticeDetail.route,
            arguments = listOf(navArgument("noticeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noticeId = backStackEntry.arguments?.getString("noticeId") ?: ""
            val notice = noticeList.find { it.id == noticeId } ?: noticeList.firstOrNull() ?: SampleNoticeData.initialNotices[0]

            NoticeDetailScreen(
                notice = notice,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.NoticeForm.route) {
            NoticeFormScreen(
                onBackClick = { navController.popBackStack() },
                onSubmit = { newNotice ->
                    noticeList = listOf(newNotice) + noticeList
                    navController.popBackStack()
                }
            )
        }
    }
}

private fun navigateBottomBar(navController: androidx.navigation.NavController, route: String) {
    val targetRoute = when (route) {
        "home" -> Screen.HomeDashboard.route
        "tasks" -> Screen.TaskList.route
        "logistics" -> Screen.LogisticsPager.route
        "finance" -> Screen.ExpenseList.route
        else -> Screen.HomeDashboard.route
    }
    navController.navigate(targetRoute) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
