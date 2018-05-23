package piuk.blockchain.android.ui.buysell.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.toolbar_general.*
import piuk.blockchain.android.R
import piuk.blockchain.android.ui.buysell.details.models.BuySellDetailsModel
import piuk.blockchain.androidcore.utils.helperfunctions.consume
import piuk.blockchain.androidcore.utils.helperfunctions.unsafeLazy
import piuk.blockchain.androidcoreui.ui.base.BaseAuthActivity
import piuk.blockchain.androidcoreui.utils.extensions.gone
import piuk.blockchain.androidcoreui.utils.extensions.visible
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.text_view_amount_text as textViewAmountDetail
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.text_view_amount_title as textViewAmountTitle
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.text_view_bank_disclaimer as textViewBankDisclaimer
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.text_view_currency_received_text as textViewCurrencyReceivedDetail
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.text_view_currency_received_title as textViewCurrencyReceivedTitle
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.text_view_exchange_rate_text as textViewExchangeRate
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.text_view_order_amount as textViewOrderAmountHeader
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.text_view_payment_fee_text as textViewPaymentFeeDetail
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.text_view_payment_fee_title as textViewPaymentFeeTitle
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.text_view_total_text as textViewTotalDetail
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.text_view_total_title as textViewTotalTitle
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.text_view_trade_id_text as textViewTradeId
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.text_view_transaction_date as textViewDateHeader
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.view_divider_bank_disclaimer as dividerBankDisclaimer
import kotlinx.android.synthetic.main.activity_coinify_transaction_detail.view_divider_total as dividerTotal

class CoinifyTransactionDetailActivity : BaseAuthActivity() {

    private val bankSellInProgressViewsToShow by unsafeLazy {
        listOf<View>(
                dividerBankDisclaimer,
                textViewBankDisclaimer
        )
    }
    private val bankSellInProgressViewsToHide by unsafeLazy {
        listOf<View>(
                dividerTotal,
                textViewTotalTitle,
                textViewTotalDetail,
                textViewPaymentFeeTitle,
                textViewPaymentFeeDetail,
                textViewAmountTitle,
                textViewAmountDetail
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coinify_transaction_detail)
        // Check Intent for validity
        require(intent.hasExtra(EXTRA_DETAILS_MODEL)) { "Intent does not contain BuySellDetailsModel, please start this Activity via the static factory method start()." }

        renderUi(intent.getParcelableExtra(EXTRA_DETAILS_MODEL))
    }

    private fun renderUi(model: BuySellDetailsModel) {
        setupToolbar(toolbar_general, model.pageTitle)

        textViewOrderAmountHeader.text = model.amountReceived
        textViewDateHeader.text = model.date
        textViewTradeId.text = model.tradeId
        textViewCurrencyReceivedTitle.text = model.currencyReceivedTitle
        textViewCurrencyReceivedDetail.text = model.amountReceived
        textViewExchangeRate.text = model.exchangeRate
        textViewAmountDetail.text = model.amountSent
        textViewPaymentFeeDetail.text = model.paymentFee
        textViewTotalDetail.text = model.totalCost

        dividerBankDisclaimer

        if (model.isSell) {
            bankSellInProgressViewsToShow.forEach { it.visible() }
            bankSellInProgressViewsToHide.forEach { it.gone() }
        }
    }

    override fun onSupportNavigateUp(): Boolean = consume { onBackPressed() }

    companion object {

        private const val EXTRA_DETAILS_MODEL =
                "piuk.blockchain.android.ui.buysell.details.EXTRA_DETAILS_MODEL"

        internal fun start(context: Context, buySellDetailsModel: BuySellDetailsModel) {
            Intent(context, CoinifyTransactionDetailActivity::class.java).apply {
                putExtra(EXTRA_DETAILS_MODEL, buySellDetailsModel)
            }.run { context.startActivity(this) }
        }

    }

}